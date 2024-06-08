package com.MP_121.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "product_table")
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="productId")
    private Long id;

    @Column (name ="name")
    private String name;

    @Column (name="description")
    private String description;

    @Column (name="price")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private UsersModel seller;

    @OneToMany(mappedBy = "product")
    private List<CartModel> cartItems;

    @OneToMany(mappedBy = "productId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderModel> orders = new ArrayList<>();

    public ProductModel() {
    }

    public ProductModel(String name, String description, UsersModel seller) {
        this.name = name;
        this.description = description;
        this.seller = seller;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public UsersModel getSellerId() {
        return seller;
    }

    public void setSellerId(UsersModel sellerId) {
        this.seller = sellerId;
    }

    public List<CartModel> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartModel> cartItems) {
        this.cartItems = cartItems;
    }

}

