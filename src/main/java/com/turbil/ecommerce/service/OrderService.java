package com.turbil.ecommerce.service;

import com.turbil.ecommerce.dto.GenericResponse;
import com.turbil.ecommerce.dto.OrderDto;
import com.turbil.ecommerce.entity.Cart;
import com.turbil.ecommerce.entity.CartDetail;
import com.turbil.ecommerce.entity.Order;
import com.turbil.ecommerce.entity.OrderDetail;
import com.turbil.ecommerce.repository.CartRepository;
import com.turbil.ecommerce.repository.OrderDetailRepository;
import com.turbil.ecommerce.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CartRepository cartRepository;
    private final CartService cartService;
    private final OrderRepository ordersRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ModelMapper modelMapper;

    public GenericResponse<OrderDto> PlaceOrder(Long customerId) { // means checkout
        try {
            // GET CART AND CART ITEMS
            Cart cart = cartRepository.findByCustomerId(customerId);
            List<CartDetail> cartDetail = cart.getCartDetails();

            // CHECK IF CART IS EMPTY
            if (cartDetail.isEmpty()) {
                return new GenericResponse<>(false, "Cart is empty", null);
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

            // Map entity to DTO
            OrderDto orderDto = modelMapper.map(order, OrderDto.class);
            return new GenericResponse<>(true, "Checkout is successful", orderDto);
        } catch (Exception e) {
            return new GenericResponse<>(false, e.getMessage(), null);
        }
    }

    public GenericResponse<OrderDto> GetOrderForCode(String code) {
        Order order = ordersRepository.findByCode(code);
        if (order != null) {
            OrderDto orderDto = modelMapper.map(order, OrderDto.class);
            return new GenericResponse<>(true, "Order found", orderDto);
        } else {
            return new GenericResponse<>(true, "Order found", null);
        }
    }

    public GenericResponse<List<OrderDto>> GetAllOrdersForCustomer(Long customerId) {
        List<Order> orders = ordersRepository.findByCustomerId(customerId);
        if (!orders.isEmpty()) {
            // Map entity to DTO
            List<OrderDto> orderDtos = new ArrayList<>();
            for (Order order : orders) {
                OrderDto orderDto = modelMapper.map(order, OrderDto.class);
                orderDtos.add(orderDto);
            }
            return new GenericResponse<>(true, "Orders found", orderDtos);
        } else {
            return new GenericResponse<>(true, "Orders found", null);
        }
    }
}
