package com.MP_121.repository;

import com.MP_121.model.CartModel;
import com.MP_121.model.UsersModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface CartRepository extends JpaRepository<CartModel, Long> {
    List<CartModel> findByBuyer(UsersModel buyer);
}

