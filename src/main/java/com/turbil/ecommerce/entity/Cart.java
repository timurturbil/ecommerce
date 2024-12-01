package com.turbil.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity(name="Cart")
@Data
public class Cart extends BaseEntity {

    @Column(nullable = false)
    private double totalPrice;

    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER)
    @ToString.Exclude // Prevents infinite loop
    private List<CartDetail> cartDetails = new ArrayList<>();
}
