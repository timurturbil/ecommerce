package com.turbil.ecommerce.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data // Lombok annotation to generate getter and setter methods
@NoArgsConstructor // Lombok annotation to generate no-args constructor
@AllArgsConstructor // Lombok annotation to generate all-args constructor. means
public class CartDto {
    private Long id;

    @NotNull(message = "Total price is required")
    private double totalPrice;

    @NotNull(message = "Customer is required")
    private CustomerDto customer;

    @NotNull(message = "Cart details is required")
    @ToString.Exclude // Prevents infinite loop
    private List<CartDetailDto> cartDetails;
}
