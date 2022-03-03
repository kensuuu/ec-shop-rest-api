package com.kensuuu.ecshop.domain.entity;

import javax.persistence.Column;
import java.time.Instant;

public abstract class BaseEntity {
    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private Boolean deleteFlag;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant createdAt;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Instant updatedAt;
}
