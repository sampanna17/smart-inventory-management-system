package com.smartinventorysystem.modules.user.entity;

import jakarta.persistence.*;
import jakarta.transaction.Status;
import lombok.Data;
import org.springframework.context.annotation.Role;

import java.time.LocalDateTime;

@Entity
@Table(name = "Users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userID;

    private String fullName;

    @Column(unique = true)
    private String email;

    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private Role role; // ADMIN, STAFF

    @Enumerated(EnumType.STRING)
    private Status status; // ACTIVE, INACTIVE

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}