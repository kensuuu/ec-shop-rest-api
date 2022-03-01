package com.kensuuu.ecshop.domain.repository;

import com.kensuuu.ecshop.domain.entity.Role;
import com.kensuuu.ecshop.security.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
