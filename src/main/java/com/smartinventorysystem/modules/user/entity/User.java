package com.smartinventorysystem.modules.user.entity;

import com.smartinventorysystem.enums.Role;
import com.smartinventorysystem.enums.Status;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "Users")
@Data
public class User {

    @Column(name = "UserID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userID;

    @Column(name = "FullName")
    private String fullName;

    @Column(name = "Email", unique = true)
    private String email;

    @Column(name = "PasswordHash")
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private Role role; // ADMIN, STAFF

    @Enumerated(EnumType.STRING)
    private Status status; // ACTIVE, INACTIVE

    @Column(name = "activation_token")
    private String activationToken;

    @Column(name = "token_expiry")
    private LocalDateTime tokenExpiry;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}