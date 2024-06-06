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
    private UsersModel buyerId;

    @ManyToOne
    @JoinColumn(name = "productId")
    private ProductModel productId;

    @Column(name="quantity")
    private int quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsersModel getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(UsersModel buyerId) {
        this.buyerId = buyerId;
    }

    public ProductModel getProductId() {
        return productId;
    }

    public void setProductId(ProductModel productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
