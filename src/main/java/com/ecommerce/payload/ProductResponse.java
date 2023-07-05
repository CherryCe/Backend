package com.ecommerce.payload;

import java.util.List;

import com.ecommerce.dto.ProductDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductResponse {

	private List<ProductDto> content;

	private int pageNumber;

	private int pageSize;

	private int totalPage;

	private boolean lastPage;
}
