package com.api.gateway.service.impl;

import com.api.gateway.model.dto.request.AuthRequest;
import com.api.gateway.model.dto.response.AuthResponse;
import com.api.gateway.model.entity.User;
import com.api.gateway.repository.UserRepository;
import com.api.gateway.service.AuthService;
import com.api.gateway.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Override
    public Mono<AuthResponse> authenticate(AuthRequest authRequest) {
        log.debug("Authenticating user with email: {}", authRequest.getEmail());

        return userRepository.findByEmailAndActive(authRequest.getEmail(), true)
                .doOnNext(user -> log.debug("Found user: {}", user.getEmail()))
                .filter(user -> {
                    boolean matches = passwordEncoder.matches(authRequest.getPassword(), user.getPassword());
                    log.debug("Password matches: {}", matches);
                    return matches;
                })
                .map(this::buildAuthResponse)
                .doOnNext(response -> log.info("Authentication successful for user: {}", response.getEmail()))
                .switchIfEmpty(Mono.error(new RuntimeException("Invalid credentials")))
                .doOnError(error -> log.error("Authentication failed for email: {}", authRequest.getEmail(), error));
    }

    private AuthResponse buildAuthResponse(User user) {
        String token = jwtUtil.generateToken(user.getEmail(), user.getId(), user.getCompanyId());

        return new AuthResponse(
                token,
                user.getEmail(),
                user.getId(),
                user.getCompanyId(),
                user.getFirstName(),
                user.getLastName(),
                expiration / 1000 // Convert to seconds
        );
    }
}
