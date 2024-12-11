package com.turbil.ecommerce.controller;

import com.turbil.ecommerce.dto.CustomerDto;
import com.turbil.ecommerce.dto.GenericResponse;
import com.turbil.ecommerce.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/customer/")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping(path = "add", produces = "application/json")
    public GenericResponse<CustomerDto> AddCustomer(@Valid @RequestBody CustomerDto customerDto) {
        return customerService.AddCustomer(customerDto);
    }
}
