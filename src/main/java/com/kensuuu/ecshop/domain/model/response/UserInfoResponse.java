package com.kensuuu.ecshop.domain.model.response;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class UserInfoResponse {
    private Long id;
    private String username;
    private String email;
    private Set<String> roles;

    public UserInfoResponse(Long id, String username, String email, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
    }
}
