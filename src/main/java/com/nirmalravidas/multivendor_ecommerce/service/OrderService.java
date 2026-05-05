package com.nirmalravidas.multivendor_ecommerce.service;

import com.nirmalravidas.multivendor_ecommerce.enums.OrderStatus;
import com.nirmalravidas.multivendor_ecommerce.exception.OrderException;
import com.nirmalravidas.multivendor_ecommerce.exception.UserException;
import com.nirmalravidas.multivendor_ecommerce.model.Address;
import com.nirmalravidas.multivendor_ecommerce.model.Cart;
import com.nirmalravidas.multivendor_ecommerce.model.Order;
import com.nirmalravidas.multivendor_ecommerce.model.User;

import java.util.List;
import java.util.Set;

public interface OrderService {
    public Set<Order> createOrder(User user, Address shippingAddress, Cart cart) throws OrderException, UserException;
    public Order findOrderById(Long orderId) throws OrderException;
    public List<Order> userOrderHistory(Long userId);
    public List<Order> getOrders(Long sellerId);
    public Order updateOrderStatus(Long orderId, OrderStatus orderStatus) throws OrderException;
    public void deleteOrder(Long orderId) throws OrderException;
    Order cancelOrder(Long orderId, User user) throws OrderException;
}
