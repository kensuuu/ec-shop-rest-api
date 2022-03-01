package com.kensuuu.ecshop.domain.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class JwtResponse {
    /** トークン. */
    private String token;
    /** トークンタイプ. */
    private String type = "Bearer";
    /** リフレッシュトークン. */
    private String refreshToken;
    /** ユーザID. */
    private Long id;
    /** ユーザ名. */
    private String username;
    /** メールアドレス. */
    private String email;
    /** 権限. */
    private List<String> roles;

    public JwtResponse(String token, String refreshToken, Long id, String username, String email,
                       List<String> roles) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
