package com.ecommerce.payload;

import java.util.List;

import com.ecommerce.dto.OrderDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderResponse {

	private List<OrderDto> content;

	private int pageNumber;

	private int pageSize;

	private int totalPage;

	private Long totalElement;

	private boolean lastPage;
}
