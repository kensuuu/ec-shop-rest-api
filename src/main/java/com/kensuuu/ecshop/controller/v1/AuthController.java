package com.kensuuu.ecshop.controller.v1;

import com.kensuuu.ecshop.security.filter.JwtUtils;
import com.kensuuu.ecshop.domain.entity.RefreshToken;
import com.kensuuu.ecshop.domain.entity.Role;
import com.kensuuu.ecshop.domain.model.request.LoginRequest;
import com.kensuuu.ecshop.domain.model.request.RefreshTokenRequest;
import com.kensuuu.ecshop.domain.model.request.SignupRequest;
import com.kensuuu.ecshop.domain.model.response.JwtResponse;
import com.kensuuu.ecshop.domain.model.response.RefreshTokenResponse;
import com.kensuuu.ecshop.domain.service.RefreshTokenService;
import com.kensuuu.ecshop.domain.service.RoleService;
import com.kensuuu.ecshop.domain.service.UserService;
import com.kensuuu.ecshop.exception.NotFoundException;
import com.kensuuu.ecshop.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/v1/auth")
@RestController
@Slf4j
public class AuthController {

    private final UserService userService;
    private final RoleService roleService;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    /**
     *
     * @param loginRequest ログイン情報
     * @param bindingResult バリデーション結果
     * @return JWTトークン
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.error("Login request has errors: {}", bindingResult.getAllErrors());
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        final Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);

        final UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        final String token = jwtUtils.generateToken(userDetails);
        final List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        final RefreshToken refreshToken = refreshTokenService.generate(userDetails.getUsername());

        return ResponseEntity.ok(new JwtResponse(token, refreshToken.getToken(), userDetails.getId(),
                userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    /**
     *
     * @param signupRequest 登録情報
     * @param bindingResult バリデーション結果
     * @return 登録情報のロケーション
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest signupRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.error("Signup request has errors: {}", bindingResult.getAllErrors());
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        URI location = UriComponentsBuilder.fromPath(("/v1/users")).build().toUri();
        if (userService.existsByUsername(signupRequest.getUsername())) {
            log.error("Username {} already exists", signupRequest.getUsername());
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(location);
            return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body("Username already exists");
        }
        if (userService.existsByEmail(signupRequest.getEmail())) {
            log.error("Email {} already exists", signupRequest.getEmail());
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(location);
            return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body("Email already exists");
        }
        Set<Role> roles = roleService.add(signupRequest.getRole());
        userService.create(signupRequest.getUsername(), signupRequest.getEmail(), passwordEncoder.encode(signupRequest.getPassword()), roles);
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.error("Refresh request has errors: {}", bindingResult.getAllErrors());
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        RefreshToken refreshToken = refreshTokenService.findByToken(refreshTokenRequest.getRefreshToken())
                .orElseThrow(() -> new NotFoundException("Invalid refresh token"));
        refreshTokenService.verifyExpiration(refreshToken);
        // create new jwt token
        return ResponseEntity.ok(new RefreshTokenResponse(refreshToken.getToken(), refreshToken.getToken()));
    }
}
