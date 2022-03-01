package com.kensuuu.ecshop.common;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class JwtConfig {
    @Value("${jwt.uri:/v1/auth/login}")
    private String uri;
    @Value("${jwt.header:Authorization}")
    private String header;
    @Value("${jwt.prefix:Bearer }")
    private String prefix;
    @Value("${jwt.expiration:#{3600000}}")
    private int expiration;
    @Value("${jwt.refreshExpiration:#{86400000}}")
    private int refreshExpiration;
    @Value("${jwt.secret:JwtSecretKey}")
    private String secret;
}
