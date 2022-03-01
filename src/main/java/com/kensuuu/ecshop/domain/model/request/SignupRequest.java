package com.kensuuu.ecshop.domain.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
public class SignupRequest {
    /** ユーザ名. */
    @NotBlank
    @Size(min = 6, max = 20)
    private String username;
    /** パスワード. */
    @NotBlank
    @Size(min = 8, max = 40)
    private String password;
    /** メールアドレス. */
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    /** 権限. */
    private String role;
}
