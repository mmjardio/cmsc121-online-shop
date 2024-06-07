package com.MP_121.model;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "users_table")

public class UsersModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "userId")
    private Long id;

    @Column (name = "login")
    private String login;

    @Column (name = "email")
    String email;

    @Column (name = "password")
    String password;

    @Column (name ="role")
    String role;

    @OneToMany(mappedBy = "seller")
    private List<ProductModel> items;

    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartModel> cartItems;
    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderModel> orders;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersModel that = (UsersModel) o;
        return Objects.equals(id, that.id) && Objects.equals(login, that.login) && Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, email, password, role);
    }

    @Override
    public String toString() {
        return "UsersModel{" +
                "userID=" + id +
                ", userName='" + login + '\'' +
                ", userEmail='" + email + '\'' +
                ", userRole='" + role + '\'' +
                '}';
    }
}
