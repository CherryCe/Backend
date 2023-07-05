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
public class UserDto {

	private int id;

	private String name;

	private String password;

	private String email;

	private String address;

	private String about;

	private String gender;

	private String phone;

	private Date date;

	private boolean active;

	private Set<RoleDto> roles = new HashSet<>();

	private CartDto cart;
}
