package com.phaseZero.catalog_service.Entity;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Entity
@Data
public class Products {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   

    @NotBlank(message = "partNumber is required")
    @Column(unique = true, nullable = false)
    private String partNumber;

    @NotBlank(message = "partName is required")
    @Column(nullable = false)
    private String partName;

    @NotBlank(message = "category is required")
    @Column(nullable = false)
    private String category;

    @PositiveOrZero(message = "price cannot be negative")
    @Column(nullable = false)
    private double price;

    @PositiveOrZero(message = "stock cannot be negative")
    @Column(nullable = false)
    private int stock;
}
