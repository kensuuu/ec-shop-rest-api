package com.kensuuu.ecshop.controller.v1;

import com.kensuuu.ecshop.domain.entity.Shop;
import com.kensuuu.ecshop.domain.service.ShopService;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/shops")
@Slf4j
public class ShopController {

    private ShopService shopService;

    @PreAuthorize("hasRole('ROLE_SELLER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("id") Long id) {
        Shop shop = shopService.findById(id);
        return ResponseEntity.ok().body(shop);
    }

    @PreAuthorize("hasRole('ROLE_SELLER')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @RequestBody Shop shop, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        URI location = UriComponentsBuilder.fromPath(("/v1/shops/" + shopService.create(shop))).build().toUri();
        return ResponseEntity.created(location).build();
    }
}
