package com.turbil.ecommerce.repository;

import com.turbil.ecommerce.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    Order findByCode(String code);
    List<Order> findByCustomerId(Long customerId);
}