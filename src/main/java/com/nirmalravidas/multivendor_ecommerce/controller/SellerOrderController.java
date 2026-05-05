package com.nirmalravidas.multivendor_ecommerce.controller;

import com.nirmalravidas.multivendor_ecommerce.enums.OrderStatus;
import com.nirmalravidas.multivendor_ecommerce.exception.OrderException;
import com.nirmalravidas.multivendor_ecommerce.exception.SellerException;
import com.nirmalravidas.multivendor_ecommerce.model.Order;
import com.nirmalravidas.multivendor_ecommerce.model.Seller;
import com.nirmalravidas.multivendor_ecommerce.response.ApiResponse;
import com.nirmalravidas.multivendor_ecommerce.service.OrderService;
import com.nirmalravidas.multivendor_ecommerce.service.SellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/seller/orders")
@RequiredArgsConstructor
public class SellerOrderController {
    private final OrderService orderService;
    private final SellerService sellerService;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders(@RequestHeader("Authorization") String jwt) throws SellerException {
        Seller seller = sellerService.getSellerProfile(jwt);
        List<Order> orders = orderService.getOrders(seller.getId());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PatchMapping("/{orderId}/status/{orderStatus}")
    public ResponseEntity<Order> updateOrderHandler(@RequestHeader("Authorization") String jwt, @PathVariable Long orderId, @PathVariable OrderStatus orderStatus) throws OrderException, SellerException {
        Seller seller = sellerService.getSellerProfile(jwt);
        Order order = orderService.findOrderById(orderId);

        if (!order.getSellerId().equals(seller.getId())) {
            throw new OrderException("You can't update this order");
        }

        if(order.getOrderStatus() == OrderStatus.CANCELLED){
            throw new OrderException("Cannot update cancelled order");
        }

        order = orderService.updateOrderStatus(orderId, orderStatus);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponse> deleteOrderHandler(@RequestHeader("Authorization") String jwt, @PathVariable Long orderId) throws OrderException, SellerException{
        Seller seller = sellerService.getSellerProfile(jwt);
        Order order = orderService.findOrderById(orderId);

        if (!order.getSellerId().equals(seller.getId())) {
            throw new OrderException("You can't delete this order");
        }

        orderService.deleteOrder(orderId);

        ApiResponse response = new ApiResponse("Order deleted successfully", true);
        log.info("Deleting order {}", orderId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
