package com.ecommerce.dto;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartDto {

	private int id;

	private Set<CartItemDto> cartItems = new HashSet<>();

	@JsonIgnore
	private UserDto user;
}
