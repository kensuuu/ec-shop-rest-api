package com.kensuuu.ecshop.controller.v1;

import com.kensuuu.ecshop.domain.entity.Product;
import com.kensuuu.ecshop.domain.service.ProductService;
import com.kensuuu.ecshop.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasRole('SELLER_ROLE')")
    @GetMapping
    public ResponseEntity<?> findAll(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                    @RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok().body(productService.findAll(page, size));
    }

    @PreAuthorize("hasRole('SELLER_ROLE')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("id") Long id) {
        Product product = productService.findById(id);
        return ResponseEntity.ok().body(product);
    }

    @PreAuthorize("hasRole('SELLER_ROLE')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                    @Valid @RequestBody Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        URI location = UriComponentsBuilder.fromPath(("/v1/products/" + productService.create(product))).build().toUri();
        return ResponseEntity.created(location).build();
    }

    @PreAuthorize("hasRole('SELLER_ROLE')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("id") Long id,
                                    @Valid @RequestBody Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        productService.update(id, product);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('SELLER_ROLE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("id") Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
