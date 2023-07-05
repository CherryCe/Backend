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
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.CartDto;
import com.ecommerce.payload.CartRequest;
import com.ecommerce.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@PostMapping("/create")
	public ResponseEntity<CartDto> create(@RequestBody CartRequest itemRequest, Principal principal) {
		CartDto add = cartService.create(itemRequest, principal.getName());
		return new ResponseEntity<CartDto>(add, HttpStatus.CREATED);
	}

	@DeleteMapping("/remove/{id}")
	public ResponseEntity<CartDto> remove(Principal principal, @PathVariable int id) {
		CartDto remove = cartService.remove(principal.getName(), id);
		return new ResponseEntity<CartDto>(remove, HttpStatus.OK);
	}

	@GetMapping("/getAll")
	public ResponseEntity<CartDto> getAll(Principal principal) {
		CartDto getAll = cartService.getAll(principal.getName());
		return new ResponseEntity<CartDto>(getAll, HttpStatus.ACCEPTED);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<CartDto> getById(@PathVariable int id) {
		CartDto getById = cartService.getById(id);
		return new ResponseEntity<CartDto>(getById, HttpStatus.OK);
	}
}