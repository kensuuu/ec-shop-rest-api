package com.kensuuu.ecshop.domain.entity;

import com.kensuuu.ecshop.security.ERole;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Getter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    public Role() {}
}
