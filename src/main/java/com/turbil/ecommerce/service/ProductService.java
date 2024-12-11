package com.turbil.ecommerce.service;

import com.turbil.ecommerce.dto.GenericResponse;
import com.turbil.ecommerce.dto.ProductDto;
import com.turbil.ecommerce.entity.Product;
import com.turbil.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public GenericResponse<ProductDto> GetProduct(Long id) {
        try {
            Product product = productRepository.findById(id).orElse(null);
            if (product != null) {
                ProductDto productDto = modelMapper.map(product, ProductDto.class);
                return new GenericResponse<>(true, "Product found", productDto);
            } else {
                return new GenericResponse<>(false, "Product not found", null);
            }
        } catch (Exception e) {
            return new GenericResponse<>(false, e.getMessage(), null);
        }
    }

    public GenericResponse<ProductDto> CreateProduct(ProductDto productDto) {
        try {
            Product product = new Product();
            product.setName(productDto.getName());
            product.setStock(productDto.getStock());
            product.setPrice(productDto.getPrice());
            productRepository.save(product);

            //Map entity to DTO
            ProductDto _productDto = modelMapper.map(product, ProductDto.class);
            return new GenericResponse<>(true, "Product created", _productDto);
        } catch (Exception e) {
            return new GenericResponse<>(false, e.getMessage(), null);
        }
    }

    public GenericResponse<ProductDto> UpdateProduct(ProductDto productDto) {
        Product product = productRepository.findById(productDto.getId()).orElse(null);
        if (product != null) {
            product.setName(productDto.getName());
            product.setStock(productDto.getStock());
            product.setPrice(productDto.getPrice());
            productRepository.save(product);

            //Map entity to DTO
            ProductDto _productDto = modelMapper.map(product, ProductDto.class);
            return new GenericResponse<>(true, "Product updated", _productDto);
        } else {
            return new GenericResponse<>(false, "Product not found", null);
        }
    }

    public GenericResponse<ProductDto> DeleteProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            productRepository.delete(product);
            ProductDto productDto = modelMapper.map(product, ProductDto.class);
            return new GenericResponse<>(true, "Product deleted", productDto);
        } else {
            return new GenericResponse<>(false, "Product not found", null);
        }
    }
}
