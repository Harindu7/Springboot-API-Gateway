package com.api.gateway.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String email;
    private String userId;
    private String companyId;
    private String firstName;
    private String lastName;
    private long expiresIn;
}
