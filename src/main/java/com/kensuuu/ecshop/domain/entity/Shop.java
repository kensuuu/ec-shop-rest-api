package com.kensuuu.ecshop.domain.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "shops")
@Getter
public class Shop extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

}
