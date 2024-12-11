package com.turbil.ecommerce.dto;

import com.turbil.ecommerce.entity.OrderDetail;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data // Lombok annotation to generate getter and setter methods
@NoArgsConstructor // Lombok annotation to generate no-args constructor
@AllArgsConstructor // Lombok annotation to generate all-args constructor. means
public class OrderDto {
    private Long id;

    @NotNull(message = "Total price is required")
    private double totalPrice;

    @NotNull(message = "Order code is required")
    private String code;

    @NotNull(message = "Customer is required")
    private CustomerDto customer;

    @NotNull(message = "Order details is required")
    private List<OrderDetailDto> orderDetails;
}
