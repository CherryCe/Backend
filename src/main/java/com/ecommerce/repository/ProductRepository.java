package com.ecommerce.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.model.Category;
import com.ecommerce.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	Page<Product> findByCategory(Category category, Pageable pageable);
}
