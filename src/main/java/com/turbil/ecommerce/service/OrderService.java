package com.turbil.ecommerce.service;

import com.turbil.ecommerce.entity.Cart;
import com.turbil.ecommerce.entity.CartDetail;
import com.turbil.ecommerce.entity.Order;
import com.turbil.ecommerce.entity.OrderDetail;
import com.turbil.ecommerce.repository.CartRepository;
import com.turbil.ecommerce.repository.OrderDetailRepository;
import com.turbil.ecommerce.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CartRepository cartRepository;
    private final CartService cartService;
    private final OrderRepository ordersRepository;
    private final OrderDetailRepository orderDetailRepository;

    public Order PlaceOrder(Long customerId) { // means checkout
        // GET CART AND CART ITEMS
        Cart cart = cartRepository.findByCustomerId(customerId);
        List<CartDetail> cartDetail = cart.getCartDetails();

        // CHECK IF CART IS EMPTY
        if (cartDetail.isEmpty()) {
            System.out.println("Cart is empty");
            return null;
        }

        // CREATE ORDER
        Order order = new Order();
        order.setCustomer(cart.getCustomer());
        order.setTotalPrice(cart.getTotalPrice());
        order.setCode("ORD" + System.currentTimeMillis());
        ordersRepository.save(order);

        // CREATE ORDER'S DETAILS
        for (CartDetail detail : cartDetail) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(detail.getProduct());
            orderDetail.setPrice(detail.getProduct().getPrice());
            orderDetail.setQuantity(detail.getQuantity());
            orderDetailRepository.save(orderDetail);
        }

        // EMPTY CART
        cartService.EmptyCart(cart.getCustomer().getId());
        return order;
    }

    public Order GetOrderForCode(String code) {
        return ordersRepository.findByCode(code);
    }

    public List<Order> GetAllOrdersForCustomer(Long customerId) {
        return ordersRepository.findByCustomerId(customerId);
    }
}
