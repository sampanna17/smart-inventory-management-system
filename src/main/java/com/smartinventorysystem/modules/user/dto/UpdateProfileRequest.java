package com.smartinventorysystem.modules.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateProfileRequest {

    private String fullName;

    @Email(message = "Invalid email format")
    private String email;
}