package com.smartinventorysystem.modules.auth.dto.response;

import lombok.Builder;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class AuthResponse  {

    private Integer userId;
    private String fullName;
    private String email;
    private String role;
    private String message;
    private String token;
    private String status;
}