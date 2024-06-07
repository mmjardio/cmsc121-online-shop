package com.MP_121.model;

import jakarta.persistence.*;
import org.apache.catalina.User;

@Entity
@Table(name = "cart_table")
public class CartModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="cartId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "buyerId")
    private UsersModel buyer;

    @ManyToOne
    @JoinColumn(name = "productId")
    private ProductModel product;

    @Column(name="quantity")
    private int quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsersModel getBuyerId() {
        return buyer;
    }

    public void setBuyerId(UsersModel buyerId) {
        this.buyer = buyerId;
    }

    public ProductModel getProductId() {
        return product;
    }

    public void setProductId(ProductModel productId) {
        this.product = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
