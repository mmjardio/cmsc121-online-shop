package com.MP_121.repository;

import com.MP_121.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {
    List<ProductModel> findBySeller_Id(Long sellerId);
    List<ProductModel> findByNameContainingIgnoreCase(String name);
}
