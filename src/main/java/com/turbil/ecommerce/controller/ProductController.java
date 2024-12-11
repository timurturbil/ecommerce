package com.turbil.ecommerce.controller;

import com.turbil.ecommerce.dto.GenericResponse;
import com.turbil.ecommerce.dto.ProductDto;
import com.turbil.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/product/")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping(path = "{id}", produces = "application/json")
    public GenericResponse<ProductDto> GetProduct(@PathVariable Long id) {
        return productService.GetProduct(id);
    }

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public GenericResponse<ProductDto> CreateProduct(@Valid @RequestBody ProductDto productDto) {
        return productService.CreateProduct(productDto);
    }

    @PutMapping(path = "", consumes = "application/json", produces = "application/json")
    public GenericResponse<ProductDto> UpdateProduct(@Valid @RequestBody ProductDto productDto) {
        return productService.UpdateProduct(productDto);
    }

    @DeleteMapping(path = "{id}", produces = "application/json")
    public GenericResponse<ProductDto> DeleteProduct(@PathVariable Long id) {
        return productService.DeleteProduct(id);
    }

}
