package com.turbil.ecommerce.controller;

import com.turbil.ecommerce.dto.CartDto;
import com.turbil.ecommerce.dto.GenericResponse;
import com.turbil.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/cart/")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping(path = "{customerId}", produces = "application/json")
    public GenericResponse<CartDto> GetCart(@PathVariable Long customerId) {
        return cartService.GetCart(customerId);
    }

    @PostMapping(path = "addProduct", produces = "application/json")
    public GenericResponse<CartDto> AddProductToCart(@RequestParam Long customerId, @RequestParam Long productId, @RequestParam int quantity) {
        return cartService.AddProductToCart(customerId, productId, quantity);
    }

    @PutMapping(path = "", produces = "application/json")
    public GenericResponse<CartDto> UpdateCart(@RequestParam Long customerId, @RequestParam Long productId, @RequestParam int quantity) {
        return cartService.UpdateCart(customerId, productId, quantity);
    }

    @DeleteMapping(path = "removeProduct", produces = "application/json")
    public GenericResponse<CartDto> RemoveProductFromCart(@RequestParam Long customerId, @RequestParam Long productId) {
        return cartService.RemoveProductFromCart(customerId, productId);
    }

    @DeleteMapping(path = "{customerId}", produces = "application/json")
    public GenericResponse<CartDto> EmptyCart(@PathVariable Long customerId) {
        return cartService.EmptyCart(customerId);
    }
}
