package com.kensuuu.ecshop.common;

import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;

@AllArgsConstructor
@Component
@Slf4j
public class JwtUtils {
    private JwtConfig jwtConfig;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = getClaims(userDetails);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration()))
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret()).compact();
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = getClaims(userDetails);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getRefreshExpiration()))
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret()).compact();
    }

    private Map<String, Object> getClaims(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
        if (roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            claims.put("admin", true);
        }
        if (roles.contains(new SimpleGrantedAuthority("ROLE_USER"))) {
            claims.put("user", true);
        }
        if (roles.contains(new SimpleGrantedAuthority("ROLE_SELLER"))) {
            claims.put("seller", true);
        }
        return claims;
    }

    public String generateFromUserName(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtConfig.getExpiration()))
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecret())
                .compact();
    }

    public String getUserNameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtConfig.getSecret())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtConfig.getSecret()).parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired");
        }
        return false;
    }

    public List<SimpleGrantedAuthority> getRolesFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtConfig.getSecret()).parseClaimsJws(token).getBody();
        List<SimpleGrantedAuthority> roles = null;
        Boolean isAdmin = claims.get("admin", Boolean.class);
        Boolean isUser = claims.get("user", Boolean.class);
        Boolean isSeller = claims.get("seller", Boolean.class);
        if (isAdmin != null && isAdmin) {
            roles = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        if (isUser != null && isUser) {
            roles = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
        if (isSeller != null && isSeller) {
            roles = List.of(new SimpleGrantedAuthority("ROLE_SELLER"));
        }
        return roles;
    }
}

