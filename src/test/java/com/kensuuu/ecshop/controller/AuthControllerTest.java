package com.kensuuu.ecshop.controller;

import com.kensuuu.ecshop.domain.service.RefreshTokenService;
import com.kensuuu.ecshop.domain.service.RoleService;
import com.kensuuu.ecshop.domain.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RefreshTokenService refreshTokenService;

    @Test
    @DisplayName("if username or password are invalid, return bad request")
    void loginRequestIsValid() throws Exception {
        mockMvc.perform(post("/api/auth/login")).
    }
}
