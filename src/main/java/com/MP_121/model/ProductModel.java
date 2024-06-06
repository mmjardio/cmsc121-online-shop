package com.MP_121.model;

import jakarta.persistence.*;

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
    @JoinColumn(name = "seller_id")
    private UsersModel sellerId;

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
        return sellerId;
    }

    public void setSellerId(UsersModel sellerId) {
        this.sellerId = sellerId;
    }
}

