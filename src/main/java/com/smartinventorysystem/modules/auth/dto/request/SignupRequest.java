package com.smartinventorysystem.modules.auth.dto.request;

import lombok.Data;

@Data
public class SignupRequest {

    private String fullName;
    private String email;
    private String password;
}
