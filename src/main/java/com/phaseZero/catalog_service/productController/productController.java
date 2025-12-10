package com.phaseZero.catalog_service.productController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.phaseZero.catalog_service.Entity.Products;
import com.phaseZero.catalog_service.exception.InvalidInputException;
import com.phaseZero.catalog_service.productService.productService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1.0/products")
@RequiredArgsConstructor
public class productController {
    
	final private productService service;
    
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Products addProduct(@Valid @RequestBody Products product) {
		return service.addProduct(product);
	}
    
	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public List<Products> fetchProducts(
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sort,
			@RequestParam(defaultValue = "false") boolean desc) {

		if (page < 1 || size < 1) {
			throw new InvalidInputException("Page and size must be positive integers");
		}

		return service.getAllProducts(page, size, sort, desc);
	}
    
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/search/{partName}")
	public List<Products> serchProductByName(@PathVariable String partName){
		return service.searchByName(partName);
	}
    

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/filter/{category}")
	public List<Products> filterByCategory(@PathVariable String category){
		return service.filterByCategory(category);
	}
    
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/sort/price")
	public List<Products> sortByPrice(){
		return service.sortByPriceAsc();
	}
    
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/inventory/value")
	public double inventoryValue() {
		return service.getTotalInventoryValue();
	}
}
