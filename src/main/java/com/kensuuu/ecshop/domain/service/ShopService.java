package com.kensuuu.ecshop.domain.service;

import com.kensuuu.ecshop.domain.entity.Shop;
import com.kensuuu.ecshop.domain.repository.ShopRepository;
import com.kensuuu.ecshop.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ShopService {
    private final ShopRepository shopRepository;

    @Transactional
    public Long create(Shop shop) {
        log.info("createShop: {}", shop.toString());
        return shopRepository.save(shop).getId();
    }

    @Transactional
    public Shop update(Shop shop) {
        log.info("updateShop: {}", shop.toString());
        return shopRepository.save(shop);
    }

    public Shop findById(Long id) {
        log.info("findById: {}", id);
        return shopRepository.findById(id).orElseThrow(() -> new NotFoundException("Shop not found"));
    }

    @Transactional
    public void delete(Long id) {
        log.info("deleteShop: {}", id);
        shopRepository.deleteById(id);
    }
}
