package com.ecommerce.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDto {

	private int id;

	private String status;

	private String payment;

	private Date delivered;

	private double amt;

	private String billing;

	private Date createAt;

	private UserDto user;

	private Set<OrderItemDto> orderItems = new HashSet<>();
}
