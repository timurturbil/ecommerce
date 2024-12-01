package com.turbil.ecommerce.service;

import com.turbil.ecommerce.entity.Cart;
import com.turbil.ecommerce.entity.CartDetail;
import com.turbil.ecommerce.entity.Customer;
import com.turbil.ecommerce.entity.Product;
import com.turbil.ecommerce.repository.CartDetailRepository;
import com.turbil.ecommerce.repository.CartRepository;
import com.turbil.ecommerce.repository.CustomerRepository;
import com.turbil.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
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

    public Cart GetCart(Long customerId) {
        return cartRepository.findByCustomerId(customerId);
    }

    public void AddProductToCart(Long customerId, Long productId, int quantity) {
        Cart cart = cartRepository.findByCustomerId(customerId);
        if (cart != null) {
            //PRODUCT
            Product product = productService.GetProduct(productId);

            //CHECK PRODUCT STOCK
            if (product.getStock() < quantity) {
                System.out.println("Product out of stock");
                return;
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

            System.out.println("Product added to cart");
        } else {
            System.out.println("Cart not found");
        }
    }

    public void UpdateCart(Long customerId, Long productId, int quantity) {
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

                System.out.println("Cart updated");
            } else {
                System.out.println("Product not found in cart");
            }
        } else {
            System.out.println("Cart not found");
        }
    }

    public void RemoveProductFromCart(Long customerId, Long productId) {
        Cart cart = cartRepository.findByCustomerId(customerId);
        if (cart != null) {
            //PRODUCT
            Product product = productService.GetProduct(productId);

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
                System.out.println("Product removed from cart");
            } else {
                System.out.println("Product not found in cart");
            }
        } else {
            System.out.println("Cart not found");
        }
    }

    @Transactional
    public void EmptyCart(Long customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId);
        if (cart != null) {
            cart.getCartDetails().clear();
            cart.setTotalPrice(0);
            cartRepository.save(cart);
            cartDetailRepository.deleteCartDetailByCartId(cart.getId());
            System.out.println("Cart emptied");
        } else {
            System.out.println("Cart not found");
        }
    }
}
