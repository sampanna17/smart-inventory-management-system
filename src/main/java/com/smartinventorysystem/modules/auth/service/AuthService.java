package com.smartinventorysystem.modules.auth.service;

import com.smartinventorysystem.modules.auth.dto.LoginRequest;
import com.smartinventorysystem.modules.auth.dto.AuthResponse;
import com.smartinventorysystem.modules.auth.dto.SignupRequest;
import com.smartinventorysystem.modules.user.dto.UpdateProfileRequest;

public interface AuthService {

    AuthResponse  signup(SignupRequest request);

    AuthResponse login(LoginRequest request);

    AuthResponse updateProfile(Integer userId, UpdateProfileRequest request);
}
