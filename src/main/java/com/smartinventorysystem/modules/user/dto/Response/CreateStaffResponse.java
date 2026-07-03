package com.smartinventorysystem.modules.user.dto.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateStaffResponse {

    private Integer userId;
    private String fullName;
    private String email;
    private String role;
    private String temporaryPassword;
}