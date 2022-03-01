package com.kensuuu.ecshop.domain.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LogoutRequest {
    /** ユーザID. */
    private Long userId;
}
