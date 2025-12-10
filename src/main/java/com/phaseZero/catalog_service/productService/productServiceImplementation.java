package com.phaseZero.catalog_service.productService;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.phaseZero.catalog_service.Entity.Products;
import com.phaseZero.catalog_service.productRepository.productRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class productServiceImplementation implements productService {
	
	final private productRepository productRepository;
	
	@Override
	public Products addProduct(Products product) {
		
		product.setPartName(product.getPartName().toLowerCase());
		
		if (productRepository.existsByPartNumber(product.getPartNumber())) {
			throw new RuntimeException("Product with this partNumber already exists");
		}
	
		if (product.getPrice() < 0 || product.getStock() < 0) {
			throw new RuntimeException("Price and stock cannot be negative");
		}

		return productRepository.save(product);
	}
	
	
	public List<Products> getAllProducts(int page, int size, String sort, boolean desc) {
		
		PageRequest pageRequest = PageRequest.of(page - 1, size,
				desc ? Sort.by(sort).descending() : Sort.by(sort).ascending());
		return productRepository.findAll(pageRequest).getContent();

	}


	@Override
	public List<Products> searchByName(String name) {
		
		return productRepository.findByPartNameContainingIgnoreCase(name);
	}


	@Override
	public List<Products> filterByCategory(String category) {
		return productRepository.findByCategoryIgnoreCase(category);
		
	}

	@Override
	public List<Products> sortByPriceAsc() {
		return productRepository.findAllByOrderByPriceAsc();
	}

	@Override
	public double getTotalInventoryValue() {
        return productRepository.findAll()
                .stream()
                .mapToDouble(p -> p.getPrice() * p.getStock())
                .sum();
    }


}
