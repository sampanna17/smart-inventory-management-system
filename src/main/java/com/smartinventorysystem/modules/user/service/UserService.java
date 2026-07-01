package com.smartinventorysystem.modules.user.service;

import com.smartinventorysystem.modules.auth.dto.AuthResponse;
import com.smartinventorysystem.modules.user.dto.UpdateProfileRequest;

public interface UserService {
    AuthResponse updateProfile(Integer userId, UpdateProfileRequest request);
    void deleteAdmin(Integer adminId);
    void deleteStaff(Integer staffId);
}
