package com.kensuuu.ecshop.domain.repository;

import com.kensuuu.ecshop.domain.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {
}
