package com.MP_121.repository;

import com.MP_121.model.OrderModel;
import com.MP_121.model.UsersModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;


public interface OrderRepository extends JpaRepository<OrderModel, Long> {
    List<OrderModel> findByBuyer(UsersModel buyerId);
}