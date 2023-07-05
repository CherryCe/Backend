package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.CategoryDto;
import com.ecommerce.payload.ApiResponse;
import com.ecommerce.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping("/create")
	public ResponseEntity<CategoryDto> create(@RequestBody CategoryDto categoryDto) {
		CategoryDto create = categoryService.create(categoryDto);
		return new ResponseEntity<CategoryDto>(create, HttpStatus.CREATED);
	}

	@DeleteMapping("/remove/{id}")
	public ResponseEntity<ApiResponse> remove(@PathVariable int id) {
		categoryService.remove(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Removed!!!", true), HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<CategoryDto> update(@RequestBody CategoryDto categoryDto, @PathVariable int id) {
		CategoryDto update = categoryService.update(categoryDto, id);
		return new ResponseEntity<CategoryDto>(update, HttpStatus.ACCEPTED);
	}

	@GetMapping("/getAll")
	public ResponseEntity<List<CategoryDto>> getAll() {
		List<CategoryDto> getAll = categoryService.getAll();
		return new ResponseEntity<List<CategoryDto>>(getAll, HttpStatus.ACCEPTED);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<CategoryDto> getById(@PathVariable int id) {
		CategoryDto getById = categoryService.getById(id);
		return new ResponseEntity<CategoryDto>(getById, HttpStatus.OK);
	}
}
