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

    private Long shopId;

    private String name;

    private Integer price;

    private Integer tax;

    private Integer order;

    private String description;

    private Integer deleteFlag;

    private Instant createdAt;

    private Instant updatedAt;

}
