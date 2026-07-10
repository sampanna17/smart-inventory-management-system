package com.smartinventorysystem.modules.user.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UpdateProfileRequest {

    private String fullName;

    @Email(message = "Invalid email format")
    private String email;
}