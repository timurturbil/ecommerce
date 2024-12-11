package com.turbil.ecommerce.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Lombok annotation to generate getter and setter methods
@NoArgsConstructor // Lombok annotation to generate no-args constructor
@AllArgsConstructor // Lombok annotation to generate all-args constructor. means
public class ProductDto {
    private Long id;

    @NotNull(message = "Product name is required")
    @Size(min = 3, message = "Product must be at least 3 characters")
    private String name;

    @NotNull(message = "Stock number is required")
    private int stock;

    @NotNull(message = "Price is required")
    private double price;
}
