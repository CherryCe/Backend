package com.ecommerce.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.payload.ApiResponse;
import com.ecommerce.payload.AppConstants;
import com.ecommerce.payload.ProductResponse;
import com.ecommerce.service.FileUploadService;
import com.ecommerce.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private FileUploadService fileUploadService;

	@Value("${product.image.path}")
	private String imagePath;

	@PostMapping("/image/{id}")
	public ResponseEntity<?> uploadImage(@PathVariable int id, @RequestParam("product_image") MultipartFile file) {
		ProductDto product = productService.getById(id);
		try {
			String uploadImage = fileUploadService.uploadImage(imagePath, file);
			product.setImage(uploadImage);
			ProductDto update = productService.update(product, id);
			return new ResponseEntity<>(update, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(Map.of("Message", "File Can Not Upload!!!"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/create/{categoryId}")
	public ResponseEntity<ProductDto> create(@RequestBody ProductDto productDto, @PathVariable int categoryId) {
		ProductDto create = productService.create(productDto, categoryId);
		return new ResponseEntity<ProductDto>(create, HttpStatus.CREATED);
	}

	@DeleteMapping("/remove/{id}")
	public ResponseEntity<ApiResponse> remove(@PathVariable int id) {
		productService.remove(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Product Removed!!!", true), HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<ProductDto> update(@RequestBody ProductDto productDto, @PathVariable int id) {
		ProductDto update = productService.update(productDto, id);
		return new ResponseEntity<ProductDto>(update, HttpStatus.ACCEPTED);
	}

	@GetMapping("/getAll")
	public ProductResponse getAll(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER_STRING, required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE_STRING, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_STRING, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR_STRING, required = false) String sortDir) {
		ProductResponse response = productService.getAll(pageNumber, pageSize, sortBy, sortDir);
		return response;
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<ProductDto> getById(@PathVariable int id) {
		ProductDto getById = productService.getById(id);
		return new ResponseEntity<ProductDto>(getById, HttpStatus.OK);
	}

	@GetMapping("/category/{id}")
	public ProductResponse getByCatgory(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER_STRING, required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE_STRING, required = false) int pageSize,
			@PathVariable int id) {
		ProductResponse response = productService.getByCategoty(pageSize, pageNumber, id);
		return response;
	}
}
