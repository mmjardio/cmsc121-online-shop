package com.MP_121.controller;

import com.MP_121.model.ProductModel;
import com.MP_121.service.ProductService;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/items")
public class ProductController {
    private ProductService productService;

    @GetMapping("/all")
    public List<ProductModel> getAllItems() {
        return productService.getAllItems();
    }

    @GetMapping("/search")
    public List<ProductModel> searchItems(@RequestParam String keyword) {
        // Implement a search functionality, for simplicity here we assume searching by name
        return productService.searchItemsByName(keyword);
    }

    @PostMapping("/add")
    public ProductModel addItem(@RequestBody ProductModel product) {
        return productService.saveItem(product);
    }

    @PutMapping("/edit")
    public ProductModel editItem(@RequestBody ProductModel product) {
        return productService.updateItem(product);
    }

    @DeleteMapping("/delete/{itemId}")
    public void deleteItem(@PathVariable Long productId) {
        productService.deleteItem(productId);
    }

    @GetMapping("/seller/{sellerId}")
    public List<ProductModel> getItemsBySeller(@PathVariable Long sellerId) {
        return productService.getItemsBySeller(sellerId);
    }

}
