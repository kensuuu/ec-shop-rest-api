package com.kensuuu.ecshop.domain.model.response;

import lombok.Getter;

@Getter
public class RefreshTokenResponse {
    /** アクセストークン. */
    private String accessToken;
    /** リフレッシュトークン. */
    private String refreshToken;
    /** トークンタイプ. */
    private String tokenType = "Bearer";

    public RefreshTokenResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
