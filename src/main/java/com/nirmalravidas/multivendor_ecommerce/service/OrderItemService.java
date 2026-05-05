package com.nirmalravidas.multivendor_ecommerce.service;

import com.nirmalravidas.multivendor_ecommerce.model.OrderItem;

public interface OrderItemService {
    OrderItem getOrderItemById(Long id) throws Exception;
}
