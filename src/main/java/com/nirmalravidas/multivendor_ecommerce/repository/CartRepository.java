package com.nirmalravidas.multivendor_ecommerce.repository;

import com.nirmalravidas.multivendor_ecommerce.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);
}
