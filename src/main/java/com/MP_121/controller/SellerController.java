package com.MP_121.controller;

import com.MP_121.model.OrderModel;
import com.MP_121.model.ProductModel;
import com.MP_121.model.UsersModel;
import com.MP_121.service.AuthenticationService;
import com.MP_121.service.OrderService;
import com.MP_121.service.ProductService;
import com.MP_121.service.UsersService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final OrderService orderService;


    @Autowired
    public SellerController(ProductService productService, UsersService usersService, AuthenticationService authenticationService, OrderService orderService) {
        this.productService = productService;
        this.usersService = usersService;
        this.authenticationService = authenticationService;
        this.orderService = orderService;
    }

    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        UsersModel seller = (UsersModel) session.getAttribute("user");
        if (seller != null && "Seller".equals(seller.getRole())) {
            List<ProductModel> products = productService.getProductsBySeller(seller);
            model.addAttribute("products", products);
        } else {
            model.addAttribute("error", "You must be logged in as a seller to view this page.");
        }
        return "seller_dashboard";
    }

    @GetMapping("/products")
    public String showProducts(HttpSession session, Model model) {
        UsersModel seller = (UsersModel) session.getAttribute("user");
        if (seller != null && "Seller".equals(seller.getRole())) {
            List<ProductModel> products = productService.getProductsBySeller(seller);
            model.addAttribute("products", products);
        } else {
            model.addAttribute("error", "You must be logged in as a seller to view this page.");
        }
        return "seller_products";
    }

    @GetMapping("/products/add")
    public String showAddProductForm(HttpSession session, Model model) {
        UsersModel seller = (UsersModel) session.getAttribute("user");
        if (seller != null && "Seller".equals(seller.getRole())) {
            model.addAttribute("product", new ProductModel());
            return "seller_add_product";
        } else {
            model.addAttribute("error", "You must be logged in as a seller to add a product.");
            return "seller_dashboard";
        }
    }

    @PostMapping("/products/add")
    public String addProduct(@RequestParam String name,
                             @RequestParam String description,
                             @RequestParam double price,
                             HttpSession session, Model model) {
        UsersModel seller = (UsersModel) session.getAttribute("user");
        if (seller != null && "Seller".equals(seller.getRole())) {
            productService.addProduct(name, description, price, seller);
            return "redirect:/seller/dashboard";
        } else {
            model.addAttribute("error", "Invalid seller credentials.");
            return "seller_add_product";
        }
    }

    @GetMapping("/products/edit/{productId}")
    public String showEditProductForm(@PathVariable Long productId, HttpSession session, Model model) {
        UsersModel seller = (UsersModel) session.getAttribute("user");
        if (seller != null && "Seller".equals(seller.getRole())) {
            ProductModel product = productService.getItemById(productId);
            if (product.getSellerId().equals(seller)) {
                model.addAttribute("product", product);
                return "seller_edit_product";
            } else {
                model.addAttribute("error", "You can only edit your own products.");
                return "seller_dashboard";
            }
        } else {
            model.addAttribute("error", "You must be logged in as a seller to edit a product.");
            return "seller_dashboard";
        }
    }

    @PostMapping("/products/edit")
    public String editProduct(@ModelAttribute("product") ProductModel product, HttpSession session, Model model) {
        UsersModel seller = (UsersModel) session.getAttribute("user");
        if (seller != null && "Seller".equals(seller.getRole())) {
            product.setSellerId(seller);
            productService.updateItem(product);
            return "redirect:/seller/products";
        } else {
            model.addAttribute("error", "Invalid seller credentials.");
            return "seller_edit_product";
        }
    }

    @GetMapping("/products/delete/{productId}")
    public String deleteProduct(@PathVariable Long productId, HttpSession session, Model model) {
        UsersModel seller = (UsersModel) session.getAttribute("user");
        if (seller != null && "Seller".equals(seller.getRole())) {
            ProductModel product = productService.getItemById(productId);
            if (product.getSellerId().equals(seller)) {
                productService.deleteItem(productId);
                return "redirect:/seller/products";
            } else {
                model.addAttribute("error", "You can only delete your own products.");
                return "seller_dashboard";
            }
        } else {
            model.addAttribute("error", "You must be logged in as a seller to delete a product.");
            return "seller_dashboard";
        }
    }

    @GetMapping("/orders")
    public String showOrders(HttpSession session, Model model) {
        UsersModel seller = (UsersModel) session.getAttribute("user");
        if (seller != null && "Seller".equals(seller.getRole())) {
            List<OrderModel> orders = orderService.getOrdersBySeller(seller);
            Map<Long, Double> orderCosts = new HashMap<>();
            for (OrderModel order : orders) {
                double cost = order.getProductId().getPrice() * order.getQuantity();
                orderCosts.put(order.getId(), cost);
            }
            model.addAttribute("orders", orders);
            model.addAttribute("orderCosts", orderCosts);
        } else {
            model.addAttribute("error", "You must be logged in as a seller to view orders.");
        }
        return "seller_orders";
    }
}
