package com.kensuuu.ecshop.controller.v1;

import com.kensuuu.ecshop.domain.model.response.UserInfoResponse;
import com.kensuuu.ecshop.domain.service.UserService;
import com.kensuuu.ecshop.exception.UnAuthorizedException;
import com.kensuuu.ecshop.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/users")
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping({"", "/"})
    @Transactional(readOnly = true)
    public ResponseEntity<?> info(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (Objects.isNull(userDetails)) {
            throw new UnAuthorizedException("User is not authorized");
        }
        return ResponseEntity.ok(new UserInfoResponse(
                userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), userDetails.getAuthorities()));
    }
}
