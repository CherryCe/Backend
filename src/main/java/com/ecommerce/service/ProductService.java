package com.ecommerce.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.payload.ProductResponse;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper mapper;

	public ProductDto create(ProductDto productDto, int categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category Not Found!!!"));
		Product product = mapper.map(productDto, Product.class);
		product.setCategory(category);
		Product save = productRepository.save(product);
		return mapper.map(save, ProductDto.class);
	}

	public void remove(int id) {
		Product findById = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product Not Found!!!"));
		productRepository.deleteById(id);
	}

	public ProductDto update(ProductDto newProduct, int id) {
		Product oldProduct = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product Not Found!!!"));
		oldProduct.setName(newProduct.getName());
		oldProduct.setPrice(newProduct.getPrice());
		oldProduct.setStock(newProduct.isStock());
		oldProduct.setQuantity(newProduct.getQuantity());
		oldProduct.setLive(newProduct.isLive());
		oldProduct.setImage(newProduct.getImage());
		oldProduct.setDescription(newProduct.getDescription());
		Product save = productRepository.save(oldProduct);
		return mapper.map(save, ProductDto.class);
	}

	public ProductResponse getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
		Sort sort = null;
		if (sortDir.trim().toLowerCase().equals("asc")) {
			sort = Sort.by(sortBy).ascending();
		} else {
			sort = Sort.by(sortBy).descending();
		}
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> page = productRepository.findAll(pageable);
		List<Product> pageProduct = page.getContent();
		List<ProductDto> productDto = pageProduct.stream().map(product -> mapper.map(product, ProductDto.class))
				.collect(Collectors.toList());
		ProductResponse response = new ProductResponse();
		response.setContent(productDto);
		response.setPageNumber(page.getNumber());
		response.setPageSize(page.getSize());
		response.setTotalPage(page.getTotalPages());
		response.setLastPage(page.isLast());
		return response;
	}

	public ProductDto getById(int id) {
		Product findById = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product Not Found!!!"));
		return mapper.map(findById, ProductDto.class);
	}

	public ProductResponse getByCategoty(int pageSize, int pageNumber, int id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category Not Found!!!"));
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Product> page = productRepository.findByCategory(category, pageable);
		List<Product> pageProduct = page.getContent();
		List<ProductDto> productDto = pageProduct.stream().map(product -> mapper.map(product, ProductDto.class))
				.collect(Collectors.toList());
		ProductResponse response = new ProductResponse();
		response.setContent(productDto);
		response.setPageNumber(page.getNumber());
		response.setPageSize(page.getSize());
		response.setTotalPage(page.getTotalPages());
		response.setLastPage(page.isLast());
		return response;
	}
}
