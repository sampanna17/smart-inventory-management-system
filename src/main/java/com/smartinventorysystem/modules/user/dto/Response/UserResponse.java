package com.smartinventorysystem.modules.user.dto.Response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {

    private Integer userID;
    private String fullName;
    private String email;
    private String role;
    private String status;
    private LocalDateTime createdAt;
}
