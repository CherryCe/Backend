package com.ecommerce.dto;

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
public class CategoryDto {

	private int id;

	private String title;

	private Set<ProductDto> products = new HashSet<>();
}
