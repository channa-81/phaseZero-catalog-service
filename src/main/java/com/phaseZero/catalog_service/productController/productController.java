package com.phaseZero.catalog_service.productController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.phaseZero.catalog_service.Entity.Products;
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
	
	@ResponseStatus(HttpStatus.FOUND)
	@GetMapping
	public List<Products> fetchProducts(int page, int size, String sort, boolean desc) {
		return service.getAllProducts(page, size, sort, desc);
	}
	
	@ResponseStatus(HttpStatus.FOUND)
	@GetMapping("/search/{partName}")
	public List<Products> serchProductByName(@PathVariable String partName){
		return service.searchByName(partName);
	}
	

	@ResponseStatus(HttpStatus.FOUND)
	@GetMapping("/filter/{category}")
	public List<Products> filterByCategory(@PathVariable String category){
		return service.filterByCategory(category);
	}
	
	@ResponseStatus(HttpStatus.FOUND)
	@GetMapping("/sort/price")
	public List<Products> sortByPrice(){
		return service.sortByPriceAsc();
	}
	
	@ResponseStatus(HttpStatus.FOUND)
	@GetMapping("/inventory/value")
	public double inventoryValue() {
		return service.getTotalInventoryValue();
	}
}
