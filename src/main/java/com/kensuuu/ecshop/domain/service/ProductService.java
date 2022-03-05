package com.kensuuu.ecshop.domain.service;

import com.kensuuu.ecshop.domain.entity.Product;
import com.kensuuu.ecshop.domain.repository.ProductRepository;
import com.kensuuu.ecshop.domain.repository.ShopRepository;
import com.kensuuu.ecshop.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public Page<Product> findAll(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        return productRepository.findAll(paging);
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
    }

    @Transactional
    public long create(Product product) {
        return productRepository.save(product).getId();
    }

    @Transactional
    public Product update(Long id, Product product) {
        Product currentProduct = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
        currentProduct.setName(product.getName());
        currentProduct.setDescription(product.getDescription());
        currentProduct.setPrice(product.getPrice());
        currentProduct.setTax(product.getTax());
        currentProduct.setShop(product.getShop());
        currentProduct.setIsActive(product.getIsActive());
        return productRepository.save(currentProduct);
    }

    @Transactional
    public void delete(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
        productRepository.delete(product);
    }
}
