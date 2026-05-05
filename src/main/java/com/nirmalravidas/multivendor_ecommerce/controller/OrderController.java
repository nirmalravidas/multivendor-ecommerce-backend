package com.nirmalravidas.multivendor_ecommerce.controller;

import com.nirmalravidas.multivendor_ecommerce.exception.OrderException;
import com.nirmalravidas.multivendor_ecommerce.exception.SellerException;
import com.nirmalravidas.multivendor_ecommerce.exception.UserException;
import com.nirmalravidas.multivendor_ecommerce.model.*;
import com.nirmalravidas.multivendor_ecommerce.service.CartService;
import com.nirmalravidas.multivendor_ecommerce.service.OrderItemService;
import com.nirmalravidas.multivendor_ecommerce.service.OrderService;
import com.nirmalravidas.multivendor_ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final UserService userService;
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<Set<Order>> createOrderHandler(
            @RequestBody Address shippingAddress,
            @RequestHeader("Authorization") String jwt)
            throws UserException, OrderException {

        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.findUserCart(user);

        Set<Order> orders = orderService.createOrder(user, shippingAddress, cart);

        return new ResponseEntity<>(orders, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> usersOrderHistory(@RequestHeader("Authorization") String jwt) throws UserException{
        User user = userService.findUserProfileByJwt(jwt);
        List<Order> orders = orderService.userOrderHistory(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@RequestHeader("Authorization") String jwt,@PathVariable Long orderId) throws OrderException, UserException{
        userService.findUserProfileByJwt(jwt);
        Order orders = orderService.findOrderById(orderId);
        return new ResponseEntity<>(orders, HttpStatus.ACCEPTED);
    }

    @GetMapping("/item/{orderItemId}")
    public ResponseEntity<OrderItem> getOrderItemById(@RequestHeader("Authorization") String jwt, @PathVariable Long orderItemId) throws Exception{
        userService.findUserProfileByJwt(jwt);
        OrderItem orderItem = orderItemService.getOrderItemById(orderItemId);
        return new ResponseEntity<>(orderItem, HttpStatus.OK);
    }

    @PutMapping("{orderId}/cancel")
    public ResponseEntity<Order> cancelOrder(@RequestHeader("Authorization") String jwt, @PathVariable Long orderId) throws UserException, OrderException, SellerException {
        User user = userService.findUserProfileByJwt(jwt);
        Order order = orderService.cancelOrder(orderId, user);

        return ResponseEntity.ok(order);
    }
}
