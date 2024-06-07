package com.MP_121.service;

import com.MP_121.model.ProductModel;
import com.MP_121.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ProductService {

    public ProductService(){

    }
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

    public ProductModel getItemById(Long productId) {
        Optional<ProductModel> productOptional = productRepository.findById(productId);
        return productOptional.orElse(null);
    }

    public List<ProductModel> searchItemsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }
}
