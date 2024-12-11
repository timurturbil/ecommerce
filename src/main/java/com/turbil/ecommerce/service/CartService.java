package com.turbil.ecommerce.service;

import com.turbil.ecommerce.dto.CartDto;
import com.turbil.ecommerce.dto.GenericResponse;
import com.turbil.ecommerce.dto.ProductDto;
import com.turbil.ecommerce.entity.Cart;
import com.turbil.ecommerce.entity.CartDetail;
import com.turbil.ecommerce.entity.Product;
import com.turbil.ecommerce.repository.CartDetailRepository;
import com.turbil.ecommerce.repository.CartRepository;
import com.turbil.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    public GenericResponse<CartDto> GetCart(Long customerId) {
        try {
            Cart cart = cartRepository.findByCustomerId(customerId);
            CartDto cartDto = modelMapper.map(cart, CartDto.class);
            return new GenericResponse<>(true, "Cart found", cartDto);
        } catch (Exception e) {
            return new GenericResponse<>(false, e.getMessage(), null);
        }
    }

    public GenericResponse<CartDto> AddProductToCart(Long customerId, Long productId, int quantity) {
        try {
            Cart cart = cartRepository.findByCustomerId(customerId);
            if (cart != null) {
                //PRODUCT
                ProductDto productDto = productService.GetProduct(productId).getData();
                Product product = new Product();
                product.setId(productDto.getId());
                product.setName(productDto.getName());
                product.setStock(productDto.getStock());
                product.setPrice(productDto.getPrice());

                //CHECK PRODUCT STOCK
                if (product.getStock() < quantity) {
                    return new GenericResponse<>(false, "Product out of stock", null);
                } else {
                    product.setStock(product.getStock() - quantity);
                    productRepository.save(product);
                }

                //CART DETAIL
                CartDetail cartDetail = new CartDetail();
                cartDetail.setCart(cart);
                cartDetail.setProduct(product);
                cartDetail.setQuantity(quantity);
                cartDetail.setPrice(product.getPrice());
                cartDetailRepository.save(cartDetail);

                cart.setTotalPrice(cart.getTotalPrice() + (product.getPrice() * quantity));
                cartRepository.save(cart);

                //Map entity to DTO
                CartDto cartDto = modelMapper.map(cart, CartDto.class);
                return new GenericResponse<>(true, "Product added to cart", cartDto);
            } else {
                return new GenericResponse<>(false, "Cart not found", null);
            }
        } catch (Exception e) {
            return new GenericResponse<>(false, e.getMessage(), null);
        }
    }

    public GenericResponse<CartDto> UpdateCart(Long customerId, Long productId, int quantity) {
        try {
            Cart cart = cartRepository.findByCustomerId(customerId);
            if (cart != null) {
                List<CartDetail> cartDetails = cart.getCartDetails();
                CartDetail cartDetail = cartDetails.stream().filter(cd -> cd.getProduct().getId().equals(productId)).findFirst().orElse(null);
                if (cartDetail != null) {
                    //UPDATE TOTAL PRICE OF CART
                    double totalPrice = cart.getTotalPrice() - (cartDetail.getPrice() * cartDetail.getQuantity());
                    cart.setTotalPrice(totalPrice + (cartDetail.getPrice() * quantity));
                    cartRepository.save(cart);

                    //UPDATE CART DETAIL
                    cartDetail.setQuantity(quantity);
                    cartDetailRepository.save(cartDetail);

                    // Map entity to DTO
                    CartDto cartDto = modelMapper.map(cart, CartDto.class);
                    return new GenericResponse<>(true, "Cart updated", cartDto);
                } else {
                    return new GenericResponse<>(false, "Product not found in cart", null);
                }
            } else {
                return new GenericResponse<>(false, "Cart not found", null);
            }
        } catch (Exception e) {
            return new GenericResponse<>(false, e.getMessage(), null);
        }
    }

    public GenericResponse<CartDto> RemoveProductFromCart(Long customerId, Long productId) {
        try {
            Cart cart = cartRepository.findByCustomerId(customerId);
            if (cart != null) {
                //PRODUCT
                ProductDto productDto = productService.GetProduct(productId).getData();
                Product product = new Product();
                product.setId(productDto.getId());
                product.setName(productDto.getName());
                product.setStock(productDto.getStock());
                product.setPrice(productDto.getPrice());

                //UPDATE PRODUCT STOCK
                //Get quantity of product in cart and add it to product stock
                int quantity = Objects.requireNonNull(cart.getCartDetails().stream().filter(cd -> cd.getProduct().getId().equals(productId)).findFirst().orElse(null)).getQuantity();
                product.setStock(product.getStock() + quantity);
                productRepository.save(product);

                //UPDATE CART
                List<CartDetail> cartDetails = cart.getCartDetails();
                CartDetail cartDetail = cartDetails.stream().filter(cd -> cd.getProduct().getId().equals(product.getId())).findFirst().orElse(null);
                if (cartDetail != null) {
                    cartDetails.remove(cartDetail);
                    cart.setTotalPrice(cart.getTotalPrice() - (cartDetail.getPrice() * cartDetail.getQuantity()));
                    cartRepository.save(cart);
                    cartDetailRepository.delete(cartDetail);

                    // Map entity to DTO
                    CartDto cartDto = modelMapper.map(cart, CartDto.class);
                    return new GenericResponse<>(true, "Product removed from cart", cartDto);
                } else {
                    return new GenericResponse<>(false, "Product not found in cart", null);
                }
            } else {
                return new GenericResponse<>(false, "Cart not found", null);
            }
        } catch (Exception e) {
            return new GenericResponse<>(false, e.getMessage(), null);
        }
    }

    @Transactional
    public GenericResponse<CartDto> EmptyCart(Long customerId) {
        try {
            Cart cart = cartRepository.findByCustomerId(customerId);
            if (cart != null) {
                cart.getCartDetails().clear();
                cart.setTotalPrice(0);
                cartRepository.save(cart);
                cartDetailRepository.deleteCartDetailByCartId(cart.getId());

                // Map entity to DTO
                CartDto cartDto = modelMapper.map(cart, CartDto.class);
                return new GenericResponse<>(true, "Cart emptied", cartDto);
            } else {
                return new GenericResponse<>(false, "Cart not found", null);
            }
        } catch (Exception e) {
            return new GenericResponse<>(false, e.getMessage(), null);
        }
    }
}
