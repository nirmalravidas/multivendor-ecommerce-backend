package com.nirmalravidas.multivendor_ecommerce.service.impl;

import com.nirmalravidas.multivendor_ecommerce.exception.OrderException;
import com.nirmalravidas.multivendor_ecommerce.model.OrderItem;
import com.nirmalravidas.multivendor_ecommerce.repository.OrderItemRepository;
import com.nirmalravidas.multivendor_ecommerce.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImplementation implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderItem getOrderItemById(Long id) throws OrderException {
        Optional<OrderItem> orderItem = orderItemRepository.findById(id);
        if (orderItem.isPresent()){
            return orderItem.get();
        }
        throw new OrderException("Order item not found");
    }
}
