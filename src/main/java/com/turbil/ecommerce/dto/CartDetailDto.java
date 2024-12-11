package com.turbil.ecommerce.dto;

import com.turbil.ecommerce.entity.CartDetail;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data // Lombok annotation to generate getter and setter methods
@NoArgsConstructor // Lombok annotation to generate no-args constructor
@AllArgsConstructor // Lombok annotation to generate all-args constructor. means
public class CartDetailDto {
    private Long id;

    @NotNull(message = "Quantity is required")
    private int quantity;

    @NotNull(message = "Price is required")
    private double price;

    @NotNull(message = "Product is required")
    private ProductDto product;
}
