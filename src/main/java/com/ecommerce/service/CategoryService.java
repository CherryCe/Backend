package com.ecommerce.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.CategoryDto;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Category;
import com.ecommerce.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper mapper;

	public CategoryDto create(CategoryDto categoryDto) {
		Category category = mapper.map(categoryDto, Category.class);
		Category save = categoryRepository.save(category);
		return mapper.map(save, CategoryDto.class);
	}

	public void remove(int id) {
		Category findById = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category Not Found!!!"));
		categoryRepository.delete(findById);
	}

	public CategoryDto update(CategoryDto newCategory, int id) {
		Category oldCategory = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category Not Found!!!"));
		oldCategory.setTitle(newCategory.getTitle());
		Category save = categoryRepository.save(oldCategory);
		return mapper.map(save, CategoryDto.class);
	}

	public List<CategoryDto> getAll() {
		List<Category> findAll = categoryRepository.findAll();
		return findAll.stream().map(category -> mapper.map(category, CategoryDto.class)).collect(Collectors.toList());
	}

	public CategoryDto getById(int id) {
		Category findById = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category Not Found!!!"));
		return mapper.map(findById, CategoryDto.class);
	}
}
