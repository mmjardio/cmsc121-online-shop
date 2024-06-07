package com.MP_121.service;

import com.MP_121.model.ProductModel;
import com.MP_121.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductModel saveItem(ProductModel item) {
        return productRepository.save(item);
    }

    public ProductModel updateItem(ProductModel item) {
        return productRepository.save(item);
    }

    public void deleteItem(Long itemId) {
        productRepository.deleteById(itemId);
    }

    public List<ProductModel> getItemsBySeller(Long sellerId) {
        return productRepository.findBySeller_Id(sellerId);
    }

    public List<ProductModel> getAllItems() {
        return productRepository.findAll();
    }

    public List<ProductModel> searchItemsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }
}
