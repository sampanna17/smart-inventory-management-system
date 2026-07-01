package com.smartinventorysystem.modules.user.dto;

import lombok.Data;

@Data
public class UpdateProfileRequest {
    private String fullName;
    private String email;
}