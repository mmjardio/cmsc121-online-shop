package com.MP_121.repository;

import com.MP_121.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {
    List<ProductModel> findBySeller_Id(Long sellerId);
    List<ProductModel> findByNameContainingIgnoreCase(String name);
}
