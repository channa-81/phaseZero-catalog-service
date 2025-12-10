package com.phaseZero.catalog_service.productService;

import java.util.List;

import com.phaseZero.catalog_service.Entity.Products;

public interface productService {
	
	Products addProduct(Products products);

    List<Products> getAllProducts(int page, int size, String sort, boolean desc);

    List<Products> searchByName(String name);

    List<Products> filterByCategory(String category);

    List<Products> sortByPriceAsc();

    double getTotalInventoryValue();

}
