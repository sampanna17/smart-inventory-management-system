package com.smartinventorysystem.modules.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse  {

    private Integer userId;
    private String fullName;
    private String email;
    private String role;
    private String message;
    private String token;
}