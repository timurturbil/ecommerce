package com.turbil.ecommerce.service;

import com.turbil.ecommerce.entity.Product;
import com.turbil.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product GetProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product CreateProduct(String name, int quantity, double price) {
        Product product = new Product();
        product.setName(name);
        product.setStock(quantity);
        product.setPrice(price);
        productRepository.save(product);

        System.out.println("Product created: " + product.getName());
        return product;
    }

    public void UpdateProduct(Long id, String name, int quantity, double price) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            product.setName(name);
            product.setStock(quantity);
            product.setPrice(price);
            productRepository.save(product);
            System.out.println("Product updated: " + product.getName());
        } else {
            System.out.println("Product not found");
        }
    }

    public void DeleteProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            productRepository.delete(product);
            System.out.println("Product deleted: " + product.getName());
        } else {
            System.out.println("Product not found");
        }
    }
}
