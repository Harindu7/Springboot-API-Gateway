package com.api.gateway.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String email;
    private String companyId;
    private String phoneNumber;
    private String password;
    private boolean active = true;
    private LocalDateTime createdAt;

    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
