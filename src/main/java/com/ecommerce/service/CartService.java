package com.ecommerce.service;

import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.CartDto;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.payload.CartRequest;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;

@Service
public class CartService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ModelMapper mapper;

	public CartDto create(CartRequest item, String username) {
		int productId = item.getProductId();
		int quantity = item.getQuantity();

		User user = userRepository.findByEmail(username)
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found!!!"));
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product Not Found!!!"));

		if (!product.isStock()) {
			new ResourceNotFoundException("Product Out Of Stock!!!");
		}
		CartItem cartItem = new CartItem();
		cartItem.setProduct(product);
		cartItem.setQuantity(quantity);
		double totalPrice = product.getPrice() * quantity;
		cartItem.setTotal(totalPrice);
		Cart cart = user.getCart();

		if (cart == null) {
			cart = new Cart();
			cart.setUser(user);
		}
		cartItem.setCart(cart);
		Set<CartItem> cartItems = cart.getCartItems();
		AtomicReference<Boolean> flag = new AtomicReference<>(false);

		Set<CartItem> newProduct = cartItems.stream().map(i -> {
			if (i.getProduct().getId() == product.getId()) {
				i.setQuantity(quantity);
				i.setTotal(totalPrice);
				flag.set(true);
			}
			return i;
		}).collect(Collectors.toSet());

		if (flag.get()) {
			cartItems.clear();
			cartItems.addAll(newProduct);
		} else {
			cartItem.setCart(cart);
			cartItems.add(cartItem);
		}

		Cart save = cartRepository.save(cart);
		return mapper.map(save, CartDto.class);
	}

	public CartDto remove(String email, int productId) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found!!!"));
		Cart cart = user.getCart();
		Set<CartItem> cartItems = cart.getCartItems();
		boolean removeIf = cartItems.removeIf(i -> i.getProduct().getId() == productId);
		Cart save = cartRepository.save(cart);
		return mapper.map(save, CartDto.class);
	}

	public CartDto getAll(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found!!!"));
		Cart cart = cartRepository.findByUser(user)
				.orElseThrow(() -> new ResourceNotFoundException("There Is No Cart!!!"));
		return mapper.map(cart, CartDto.class);
	}

	public CartDto getById(int id) {
		Cart findById = cartRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cart Not Found!!!"));
		return mapper.map(findById, CartDto.class);
	}
}
