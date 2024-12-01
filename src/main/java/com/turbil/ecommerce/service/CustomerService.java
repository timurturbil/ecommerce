package com.turbil.ecommerce.service;

import com.turbil.ecommerce.entity.Cart;
import com.turbil.ecommerce.entity.Customer;
import com.turbil.ecommerce.repository.CartRepository;
import com.turbil.ecommerce.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;

    public Customer AddCustomer(String username, String password) {

        // CUSTOMER
        Customer customer = new Customer();
        customer.setUsername(username);
        customer.setPassword(password);
        customerRepository.save(customer);

        // CART
        Cart cart = new Cart();
        cart.setTotalPrice(0);
        cart.setCustomer(customer);
        cartRepository.save(cart);

        System.out.println("Customer added: " + customer.getUsername());
        return customer;
    }

    //TODO: implement other services
}
