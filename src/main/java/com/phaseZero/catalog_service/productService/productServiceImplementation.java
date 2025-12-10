package com.phaseZero.catalog_service.productService;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.phaseZero.catalog_service.Entity.Products;
import com.phaseZero.catalog_service.exception.DuplicateResourceException;
import com.phaseZero.catalog_service.exception.InvalidInputException;
import com.phaseZero.catalog_service.exception.NoRecordsFoundException;
import com.phaseZero.catalog_service.exception.ResourceNotFoundException;
import com.phaseZero.catalog_service.productRepository.productRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class productServiceImplementation implements productService {
    
	final private productRepository productRepository;
    
	@Override
	public Products addProduct(Products product) {
		if (product == null) {
			throw new InvalidInputException("Product must not be null");
		}

		if (product.getPartNumber() == null || product.getPartNumber().isBlank()) {
			throw new InvalidInputException("partNumber is required");
		}

		if (product.getPartName() == null || product.getPartName().isBlank()) {
			throw new InvalidInputException("partName is required");
		}

		if (product.getCategory() == null || product.getCategory().isBlank()) {
			throw new InvalidInputException("category is required");
		}

		product.setPartName(product.getPartName().toLowerCase());

		if (productRepository.existsByPartNumber(product.getPartNumber())) {
			throw new DuplicateResourceException("Product with this partNumber already exists");
		}

		if (product.getPrice() < 0 || product.getStock() < 0) {
			throw new InvalidInputException("Price and stock cannot be negative");
		}

		return productRepository.save(product);
	}
    
    
	public List<Products> getAllProducts(int page, int size, String sort, boolean desc) {

	   
	    if (page < 1 || size < 1) {
	        throw new InvalidInputException("Page and size must be positive integers");
	    }

	    
	    if (sort == null || sort.isBlank()) {
	        sort = "id";  
	    }

	 
	    Sort sortOrder = desc ? Sort.by(sort).descending() : Sort.by(sort).ascending();

	   
	    PageRequest pageable = PageRequest.of(page - 1, size, sortOrder);

	    Page<Products> productPage = productRepository.findAll(pageable);

	    if (productPage.isEmpty()) {
	        throw new NoRecordsFoundException("No products found. Please insert products.");
	    }

	    return productPage.getContent();
	}


	@Override
	public List<Products> searchByName(String name) {
		if (name == null || name.isBlank()) {
			throw new InvalidInputException("Search name must not be blank");
		}

		List<Products> results = productRepository.findByPartNameContainingIgnoreCase(name);
		if (results == null || results.isEmpty()) {
			throw new ResourceNotFoundException("No products found matching: '" + name + "'");
		}

		return results;
	}

	@Override
	public List<Products> filterByCategory(String category) {
		if (category == null || category.isBlank()) {
			throw new InvalidInputException("Category must not be blank");
		}

		List<Products> results = productRepository.findByCategoryIgnoreCase(category);
		if (results == null || results.isEmpty()) {
			throw new ResourceNotFoundException("No products found in category: '" + category + "'");
		}

		return results;
		
	}

	@Override
	public List<Products> sortByPriceAsc() {
		List<Products> results = productRepository.findAllByOrderByPriceAsc();
		if (results == null || results.isEmpty()) {
			throw new ResourceNotFoundException("No products available to sort by price");
		}
		return results;
	}

	@Override
	public double getTotalInventoryValue() {
        return productRepository.findAll()
                .stream()
                .mapToDouble(p -> p.getPrice() * p.getStock())
                .sum();
    }


}
