package com.turbil.ecommerce.repository;

import com.turbil.ecommerce.entity.CartDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartDetailRepository extends CrudRepository<CartDetail, Long> {
    void deleteCartDetailByCartId(Long cartId);
}