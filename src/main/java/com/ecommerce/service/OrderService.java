package com.ecommerce.service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.OrderDto;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
import com.ecommerce.model.User;
import com.ecommerce.payload.OrderRequest;
import com.ecommerce.payload.OrderResponse;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.UserRepository;

@Service
public class OrderService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ModelMapper mapper;

	public OrderDto create(OrderRequest request, String email) {
		int cartId = request.getCartId();
		String address = request.getAddress();

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found!!!"));
		Cart cart = cartRepository.findById(cartId)
				.orElseThrow(() -> new ResourceNotFoundException("Cart Not Found!!!"));

		Set<CartItem> cartItems = cart.getCartItems();
		Order order = new Order();
		AtomicReference<Double> totalPrice = new AtomicReference<Double>(0.0);

		Set<OrderItem> orderitems = cartItems.stream().map((cartItem) -> {
			OrderItem orderItem = new OrderItem();
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setTotal(cartItem.getTotal());
			orderItem.setOrder(order);
			totalPrice.set(totalPrice.get() + orderItem.getTotal());
			int productId = orderItem.getProduct().getId();
			return orderItem;
		}).collect(Collectors.toSet());

		order.setBilling(address);
		order.setDelivered(null);
		order.setStatus("CREATED");
		order.setPayment("NOT PAID");
		order.setUser(user);
		order.setOrderItems(orderitems);
		order.setAmt(totalPrice.get());
		order.setCreateAt(new Date());
		Order save;

		if (order.getAmt() > 0) {
			save = orderRepository.save(order);
			cart.getCartItems().clear();
			cartRepository.save(cart);
		} else {
			throw new ResourceNotFoundException("Please Add Cart And Place Order!!!");
		}

		return mapper.map(save, OrderDto.class);
	}

	public void remove(int id) {
		Order findById = orderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Order Not Found!!!"));
		orderRepository.delete(findById);
	}

	public OrderResponse getAll(int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Order> getAll = orderRepository.findAll(pageable);
		List<Order> content = getAll.getContent();
		List<OrderDto> collect = content.stream().map(i -> mapper.map(i, OrderDto.class)).collect(Collectors.toList());
		OrderResponse response = new OrderResponse();
		response.setContent(collect);
		response.setPageNumber(getAll.getNumber());
		response.setPageSize(getAll.getSize());
		response.setTotalPage(getAll.getTotalPages());
		response.setLastPage(getAll.isLast());
		response.setTotalElement(getAll.getTotalElements());
		return response;
	}

	public OrderDto getById(int id) {
		Order findById = orderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Order Not Found!!!"));
		return mapper.map(findById, OrderDto.class);
	}
}
