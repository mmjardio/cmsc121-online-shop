package com.MP_121.controller;

import com.MP_121.model.UsersModel;
import com.MP_121.service.AuthenticationService;
import com.MP_121.service.UsersService;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class LoginController {
    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login-role-choose";
    }

    @GetMapping("/buyer/login")
    public String showBuyerLoginForm(Model model) {
        return "buyer_login_page"; // buyer/login.html template
    }

    @GetMapping("/seller/login")
    public String showSellerLoginForm(Model model) {
        return "seller_login_page"; // seller/login.html template
    }

    @Autowired
    private UsersService usersService;

    @PostMapping("/buyer")
    public String loginBuyer(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        UsersModel user = usersService.authenticate(email, password);
        if (user != null && "Buyer".equals(user.getRole())) {
            session.setAttribute("user", user);
            return "redirect:/buyer/dashboard";
        } else {
            model.addAttribute("error", "Invalid buyer credentials.");
            return "buyer_login_page";
        }
    }

    @PostMapping("/seller")
    public String loginSeller(@RequestParam String email, @RequestParam String password, HttpSession session, Model model) {
        UsersModel user = usersService.authenticate(email, password);
        if (user != null && "Seller".equals(user.getRole())) {
            session.setAttribute("user", user);
            return "redirect:/seller/dashboard";
        } else {
            model.addAttribute("error", "Invalid seller credentials.");
            return "seller_login_page";
        }
    }
}
