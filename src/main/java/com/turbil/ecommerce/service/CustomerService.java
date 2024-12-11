package com.turbil.ecommerce.service;

import com.turbil.ecommerce.dto.CustomerDto;
import com.turbil.ecommerce.dto.GenericResponse;
import com.turbil.ecommerce.entity.Cart;
import com.turbil.ecommerce.entity.Customer;
import com.turbil.ecommerce.repository.CartRepository;
import com.turbil.ecommerce.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;

    public GenericResponse<CustomerDto> AddCustomer(CustomerDto customerDto) {
        try {
            // CUSTOMER
            Customer customer = new Customer();
            customer.setUsername(customerDto.getUsername());
            customer.setPassword(customerDto.getPassword());
            customerRepository.save(customer);

            // CART
            Cart cart = new Cart();
            cart.setTotalPrice(0);
            cart.setCustomer(customer);
            cartRepository.save(cart);

            // Map entity to DTO
            CustomerDto _customerDto = modelMapper.map(customer, CustomerDto.class);
            return new GenericResponse<>(true, "Customer added", _customerDto);
        } catch (Exception e) {
            return new GenericResponse<>(false, e.getMessage(), null);
        }
    }
}
