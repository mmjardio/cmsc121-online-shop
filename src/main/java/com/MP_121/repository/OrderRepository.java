package com.MP_121.repository;

import com.MP_121.model.OrderModel;
import com.MP_121.model.ProductModel;
import com.MP_121.model.UsersModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Long> {
    List<OrderModel> findByBuyer(UsersModel buyerId);
    List<OrderModel> findBySeller(UsersModel seller);

    boolean existsByProductId(ProductModel productId);
}