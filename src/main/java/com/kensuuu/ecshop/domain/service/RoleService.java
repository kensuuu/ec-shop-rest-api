package com.kensuuu.ecshop.domain.service;

import com.kensuuu.ecshop.domain.entity.Role;
import com.kensuuu.ecshop.domain.repository.RoleRepository;
import com.kensuuu.ecshop.exception.NotFoundException;
import com.kensuuu.ecshop.security.ERole;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@Service
@Slf4j
public class RoleService {
    private final RoleRepository roleRepository;

    public Set<Role> add(String str) {
        Set<Role> roles = new HashSet<>();
        if (Objects.isNull(str)) {
            log.info("default role is added");
            Role role = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            roles.add(role);
        } else {
            switch (str) {
                case "admin":
                    Role admin = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new NotFoundException("Role not found"));
                    roles.add(admin);
                    break;
                case "seller":
                    Role seller = roleRepository.findByName(ERole.ROLE_SELLER)
                            .orElseThrow(() -> new NotFoundException("Role not found"));
                    roles.add(seller);
                    break;
                default:
                    Role user = roleRepository.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new NotFoundException("Role not found"));
                    roles.add(user);
                    break;
            }
        }
        return roles;
    }
}
