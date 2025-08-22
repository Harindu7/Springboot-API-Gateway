package com.api.gateway.controller;

import com.api.gateway.model.dto.request.AuthRequest;
import com.api.gateway.model.dto.response.AuthResponse;
import com.api.gateway.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication management APIs")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticate user and return JWT token")
    public Mono<ResponseEntity<AuthResponse>> login(@Valid @RequestBody AuthRequest authRequest) {
        return authService.authenticate(authRequest)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.badRequest().build());
    }

    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Check if API Gateway is running")
    public Mono<ResponseEntity<String>> health() {
        return Mono.just(ResponseEntity.ok("API Gateway is running"));
    }
}
