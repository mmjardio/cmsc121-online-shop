package com.MP_121.service;

import com.MP_121.model.OrderModel;
import com.MP_121.model.UsersModel;
import com.MP_121.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public OrderModel placeOrder(OrderModel order) {
        return orderRepository.save(order);
    }

    public List<OrderModel> getOrdersByBuyer(UsersModel buyerId) {
        return orderRepository.findByBuyer(buyerId);
    }
}