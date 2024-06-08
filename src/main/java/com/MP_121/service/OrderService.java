package com.MP_121.service;

import com.MP_121.model.OrderModel;
import com.MP_121.model.ProductModel;
import com.MP_121.model.UsersModel;
import com.MP_121.repository.OrderRepository;
import jakarta.transaction.Transactional;
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

    public List<OrderModel> getOrdersBySeller(UsersModel seller) {
        return orderRepository.findBySeller(seller);
    }

    public boolean hasOrdersForProduct(ProductModel productId) {
        return orderRepository.existsByProductId(productId);
    }

}