package com.kensuuu.ecshop.domain.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "products")
@Getter
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shop_id", referencedColumnName = "id")
    private Shop shop;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer tax;

    @Column(nullable = false, columnDefinition = "DEFAULT 0")
    private Integer order;

    @Column(columnDefinition = "TEXT")
    private String description;

}
