package com.MP_121.service;

import com.MP_121.model.ProductModel;
import com.MP_121.model.UsersModel;
import com.MP_121.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ProductService {

    public ProductService(){

    }

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductModel saveItem(ProductModel product) {
        return productRepository.save(product);
    }

    public ProductModel addProduct(String name, String description, double price, UsersModel seller) {
        if (name == null || description == null || seller == null) {
            return null;
        } else {
            ProductModel productModel = new ProductModel();
            productModel.setName(name);
            productModel.setDescription(description);
            productModel.setPrice(price);
            productModel.setSellerId(seller);
            return productRepository.save(productModel);
        }
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
