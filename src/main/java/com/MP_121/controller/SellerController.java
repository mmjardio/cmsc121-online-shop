package com.MP_121.controller;

import com.MP_121.model.ProductModel;
import com.MP_121.model.UsersModel;
import com.MP_121.service.AuthenticationService;
import com.MP_121.service.ProductService;
import com.MP_121.service.UsersService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/seller")
public class SellerController {
    @Autowired
    private final ProductService productService;
    @Autowired
    private final UsersService usersService;
    @Autowired

    private final AuthenticationService authenticationService;


    @Autowired
    public SellerController(ProductService productService, UsersService usersService, AuthenticationService authenticationService) {
        this.productService = productService;
        this.usersService = usersService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/seller/dashboard")
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
    public String addProduct(@RequestParam String name,
                             @RequestParam String description,
                             @RequestParam double price,
                             HttpSession session, Model model) {
        UsersModel seller = (UsersModel) session.getAttribute("user");
        if (seller != null && "Seller".equals(seller.getRole())) {
            productService.addProduct(name, description, price, seller);
            return "seller_dashboard";
        } else {
            model.addAttribute("error", "Invalid seller credentials.");
            return "seller_add_product";
        }
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
