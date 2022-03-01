package com.kensuuu.ecshop.domain.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
public class RefreshTokenRequest {
    /** リフレッシュトークン. */
    @NotBlank(message = "refreshToken is required")
    private String refreshToken;
}
