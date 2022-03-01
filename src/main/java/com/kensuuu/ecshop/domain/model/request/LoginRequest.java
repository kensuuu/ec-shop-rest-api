package com.kensuuu.ecshop.domain.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
public class LoginRequest {
    /** ユーザ名. */
    @NotBlank(message = "username is required")
    @Size(min = 6, max = 20, message = "username must be between 6 and 20 characters")
    private String username;
    /** パスワード. */
    @NotBlank(message = "password is required")
    @Size(min = 8, max = 40, message = "password must be between 8 and 40 characters")
    private String password;
}
