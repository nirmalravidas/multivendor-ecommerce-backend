package com.nirmalravidas.multivendor_ecommerce.repository;

import com.nirmalravidas.multivendor_ecommerce.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
