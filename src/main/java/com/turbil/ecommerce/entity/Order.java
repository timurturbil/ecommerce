package com.turbil.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "`Order`") // order is a reserved keyword in SQL
@Data
public class Order extends BaseEntity {

    @Column(nullable = false)
    private double totalPrice;

    @Column(nullable = false)
    private String code;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    @ToString.Exclude // Prevents infinite loop
    private List<OrderDetail> orderDetails = new ArrayList<>();
}
