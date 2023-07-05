package com.ecommerce.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.OrderDto;
import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.AppConstants;
import com.ecommerce.payload.OrderRequest;
import com.ecommerce.payload.OrderResponse;
import com.ecommerce.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping("/create")
	public ResponseEntity<OrderDto> create(@RequestBody OrderRequest request, Principal principal) {
		OrderDto create = orderService.create(request, principal.getName());
		return new ResponseEntity<OrderDto>(create, HttpStatus.CREATED);
	}

	@DeleteMapping("/remove/{id}")
	public ResponseEntity<ApiResponse> remove(@PathVariable int id) {
		orderService.remove(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Order Removed!!!", true), HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public OrderResponse getAll(
			@RequestParam(defaultValue = AppConstants.PAGE_SIZE_STRING, value = "pageSize", required = false) int pageSize,
			@RequestParam(defaultValue = AppConstants.PAGE_NUMBER_STRING, value = "pageNumber", required = false) int pageNumber) {
		OrderResponse response = orderService.getAll(pageNumber, pageSize);
		return response;
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<OrderDto> getById(@PathVariable int id) {
		OrderDto getById = orderService.getById(id);
		return new ResponseEntity<OrderDto>(getById, HttpStatus.OK);
	}
}
