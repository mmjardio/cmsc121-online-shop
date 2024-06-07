package com.MP_121.controller;

import com.MP_121.model.ProductModel;
import com.MP_121.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/seller")
public class SellerController {
    private final ProductService productService;

    @Autowired
    public SellerController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        // Fetch all products belonging to the logged-in seller
        List<ProductModel> products = productService.getAllItems();
        model.addAttribute("products", products);
        return "seller_dashboard";
    }

    @GetMapping("/products")
    public String showProducts(Model model) {
        // Fetch all products belonging to the logged-in seller
        List<ProductModel> products = productService.getAllItems();
        model.addAttribute("products", products);
        return "seller_products";
    }

    @GetMapping("/products/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new ProductModel());
        return "seller_add_product";
    }

    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute("product") ProductModel product) {
        productService.saveItem(product);
        return "redirect:/seller/products";
    }

    @GetMapping("/products/edit/{productId}")
    public String showEditProductForm(@PathVariable Long productId, Model model) {
        ProductModel product = productService.getItemById(productId);
        model.addAttribute("product", product);
        return "seller_edit_product";
    }

    @PostMapping("/products/edit")
    public String editProduct(@ModelAttribute("product") ProductModel product) {
        productService.updateItem(product);
        return "redirect:/seller/products";
    }

    @GetMapping("/products/delete/{productId}")
    public String deleteProduct(@PathVariable Long productId) {
        productService.deleteItem(productId);
        return "redirect:/seller/products";
    }

    @GetMapping("/orders")
    public String showOrders(Model model) {
        // Implement logic to fetch orders related to the logged-in seller
        // Example: List<OrderModel> orders = orderService.getOrdersBySellerId(sellerId);
        // model.addAttribute("orders", orders);
        return "seller_orders";
    }

}
