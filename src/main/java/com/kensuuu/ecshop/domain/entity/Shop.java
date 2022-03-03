package com.kensuuu.ecshop.domain.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "shops")
@Getter
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String name;

    private Long categoryId;

    private String description;

    private Integer deleteFlag;

    private Instant createdAt;

    private Instant updatedAt;
}
