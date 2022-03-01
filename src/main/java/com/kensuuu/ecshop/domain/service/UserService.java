package com.kensuuu.ecshop.domain.service;

import com.kensuuu.ecshop.domain.entity.Role;
import com.kensuuu.ecshop.domain.entity.User;
import com.kensuuu.ecshop.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@AllArgsConstructor
@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public void create(String name, String email, String password, Set<Role> roles) {
        User user = new User(name, email, password, roles);
        log.info("Creating user: {}", user);
        userRepository.save(user);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
