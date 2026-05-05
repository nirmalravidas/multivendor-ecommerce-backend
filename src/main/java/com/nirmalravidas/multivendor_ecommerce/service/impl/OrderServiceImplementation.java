package com.nirmalravidas.multivendor_ecommerce.service.impl;

import com.nirmalravidas.multivendor_ecommerce.enums.OrderStatus;
import com.nirmalravidas.multivendor_ecommerce.exception.OrderException;
import com.nirmalravidas.multivendor_ecommerce.exception.UserException;
import com.nirmalravidas.multivendor_ecommerce.model.*;
import com.nirmalravidas.multivendor_ecommerce.repository.AddressRepository;
import com.nirmalravidas.multivendor_ecommerce.repository.OrderItemRepository;
import com.nirmalravidas.multivendor_ecommerce.repository.OrderRepository;
import com.nirmalravidas.multivendor_ecommerce.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImplementation implements OrderService {

    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public Set<Order> createOrder(User user, Address shippingAddress, Cart cart) throws OrderException, UserException {

        if(!user.getAddresses().contains(shippingAddress)){
            user.getAddresses().add(shippingAddress);
        }

        addressRepository.save(shippingAddress);

        Map<Long, List<CartItem>> itemBySeller = new HashMap<>();
        for (CartItem item : cart.getCartItems()){
            Long sellerId = item.getProduct().getSeller().getId();
            if (!itemBySeller.containsKey(sellerId)){
                itemBySeller.put(sellerId, new ArrayList<>());
            }
            itemBySeller.get(sellerId).add(item);
        }

        Set<Order> orders = new HashSet<>();
        for (Long sellerId : itemBySeller.keySet()){
            List<CartItem> cartItems = itemBySeller.get(sellerId);
            int totalOrderPrice = 0;
            int totalMrpPrice = 0;
            int totalItem = 0;

            for (CartItem item : cartItems) {
                totalOrderPrice = totalOrderPrice + item.getSellingPrice()*item.getQuantity();
                totalMrpPrice = totalMrpPrice + item.getMrpPrice()*item.getQuantity();
                totalItem = totalItem + item.getQuantity();
            }

            Order createdOrder = new Order();
            createdOrder.setUser(user);
            createdOrder.setSellerId(sellerId);
            createdOrder.setTotalMrpPrice(totalMrpPrice);
            createdOrder.setTotalSellingPrice(totalOrderPrice);
            createdOrder.setTotalItem(totalItem);
            createdOrder.setAddress(shippingAddress);
            createdOrder.setOrderStatus(OrderStatus.PENDING);

            Order savedOrder = orderRepository.save(createdOrder);
            orders.add(savedOrder);

            for (CartItem item : cartItems){

                OrderItem orderItem = new OrderItem();

                orderItem.setOrder(savedOrder);
                orderItem.setMrpPrice(item.getMrpPrice());
                orderItem.setSellingPrice(item.getSellingPrice());
                orderItem.setProduct(item.getProduct());
                orderItem.setQuantity(item.getQuantity());
                orderItem.setSize(item.getSize());
                orderItem.setUserId(item.getUserId());

                savedOrder.getOrderItems().add(orderItem);

                orderItemRepository.save(orderItem);
            }
        }

        return orders;
    }

    @Override
    public Order findOrderById(Long orderId) throws OrderException {
        Optional<Order> optional = orderRepository.findById(orderId);
        if (optional.isPresent()){
            return optional.get();
        }

        throw new OrderException("Order not exist with id");
    }

    @Override
    public List<Order> userOrderHistory(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public List<Order> getOrders(Long sellerId) {
        return orderRepository.findBySellerIdOrderDateDesc(sellerId);
    }

    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus orderStatus) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus(orderStatus);
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        orderRepository.deleteById(orderId);
    }

    @Override
    public Order cancelOrder(Long orderId, User user) throws OrderException {
        Order order = this.findOrderById(orderId);
        if (!user.getId().equals(order.getUser().getId())){
            throw new OrderException("you can't perform this action "+orderId);
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }
}
