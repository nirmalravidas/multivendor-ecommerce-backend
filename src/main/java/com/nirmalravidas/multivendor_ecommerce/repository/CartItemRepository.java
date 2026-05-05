package com.nirmalravidas.multivendor_ecommerce.repository;

import com.nirmalravidas.multivendor_ecommerce.model.Cart;
import com.nirmalravidas.multivendor_ecommerce.model.CartItem;
import com.nirmalravidas.multivendor_ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartAndProductAndSize(Cart cart, Product product, String size);
}
