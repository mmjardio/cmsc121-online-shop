package com.MP_121.controller;
import com.MP_121.model.OrderModel;
import com.MP_121.model.UsersModel;
import com.MP_121.service.CartService;
import com.MP_121.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @PostMapping("/place")
    public OrderModel placeOrder(@RequestBody OrderModel order) {
        // Assuming an order is placed from items in the cart
        cartService.clearCart(order.getBuyer());
        return orderService.placeOrder(order);
    }

    @GetMapping("/{buyerId}")
    public List<OrderModel> getOrdersByBuyer(@PathVariable UsersModel buyerId) {
        return orderService.getOrdersByBuyer(buyerId);
    }
}