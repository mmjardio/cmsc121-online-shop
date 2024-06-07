package com.MP_121.model;

import jakarta.persistence.*;

@Entity
@Table(name = "order_table")
public class OrderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "buyerId")
    private UsersModel buyer;

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
        return buyer;
    }

    public void setBuyerId(UsersModel buyerId) {
        this.buyer = buyerId;
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
