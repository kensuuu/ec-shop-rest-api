package com.kensuuu.ecshop.domain.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "products")
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long price;

    private String description;

    private Instant createdAt;

    private Instant updatedAt;

    @ManyToMany
    @JoinTable(name = "product_category", joinColumns = @JoinColumn(name = "productid"), inverseJoinColumns = @JoinColumn(name = "categoryid"))
    private Set<Category> categories;
}
