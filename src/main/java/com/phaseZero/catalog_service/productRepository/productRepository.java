package com.phaseZero.catalog_service.productRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phaseZero.catalog_service.Entity.Products;

public interface productRepository extends JpaRepository<Products, String>{

	boolean existsByPartNumber(String partNumber);

	List<Products> findByPartNameContainingIgnoreCase(String name);

	List<Products> findByCategoryIgnoreCase(String category);

	List<Products> findAllByOrderByPriceAsc();

}
