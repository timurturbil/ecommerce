package com.turbil.ecommerce.controller;

import com.turbil.ecommerce.dto.GenericResponse;
import com.turbil.ecommerce.dto.OrderDto;
import com.turbil.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/order/")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping(path = "checkout/{customerId}")
    public GenericResponse<OrderDto> PlaceOrder(@PathVariable Long customerId) {
        return orderService.PlaceOrder(customerId);
    }

    @GetMapping(path = "{code}")
    public GenericResponse<OrderDto> GetOrder(@PathVariable String code) {
        return orderService.GetOrderForCode(code);
    }

    @GetMapping(path = "all/{customerId}")
    public GenericResponse<List<OrderDto>> GetAllOrders(@PathVariable Long customerId) {
        return orderService.GetAllOrdersForCustomer(customerId);
    }
}
