package com.MP_121.controller;

import com.MP_121.model.UsersModel;
import com.MP_121.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsersController {
    @Autowired
    private UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model){
        model.addAttribute("registerRequest", new UsersModel());
        return "register_page";
    }

    @GetMapping("/loginold")
    public String getLoginPage(Model model){
        model.addAttribute("loginRequest", new UsersModel());
        return "login_page";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String login, @RequestParam String email, @RequestParam String password, @RequestParam String role, RedirectAttributes redirectAttributes) {
        try {
            UsersModel newUser = usersService.registerUser(login, email, password, role);
            redirectAttributes.addFlashAttribute("success", "User registered successfully!");
            return "redirect:/register"; // Redirect to the registration page
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error occurred during registration: " + e.getMessage());
            return "redirect:/register"; // Redirect to the registration page in case of error
        }
    }

    @PostMapping("/loginold")
    public UsersModel authenticateUser(@RequestParam String email, @RequestParam String password) {
        return usersService.authenticate(email, password);
    }


}
