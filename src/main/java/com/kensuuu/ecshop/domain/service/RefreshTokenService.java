package com.kensuuu.ecshop.domain.service;

import com.kensuuu.ecshop.security.filter.JwtUtils;
import com.kensuuu.ecshop.domain.entity.RefreshToken;
import com.kensuuu.ecshop.domain.entity.User;
import com.kensuuu.ecshop.domain.repository.RefreshTokenRepository;
import com.kensuuu.ecshop.domain.repository.UserRepository;
import com.kensuuu.ecshop.exception.NotFoundException;
import com.kensuuu.ecshop.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken generate(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found from username: " + username));
        final String token = jwtUtils.generateRefreshToken(UserDetailsImpl.of(user));
        RefreshToken refreshToken = new RefreshToken(user, token, Instant.now());
        return refreshTokenRepository.save(refreshToken);
    }

    public void verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken.isExpired()) {
            refreshTokenRepository.delete(refreshToken);
            throw new IllegalArgumentException("Refresh token expired");
        }
    }

    @Transactional
    public int delete(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found from id: " + userId));
        return refreshTokenRepository.deleteByUser(user);
    }
}
