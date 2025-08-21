package com.api.gateway.service;

import com.api.gateway.model.dto.request.AuthRequest;
import com.api.gateway.model.dto.response.AuthResponse;
import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<AuthResponse> authenticate(AuthRequest authRequest);
}
