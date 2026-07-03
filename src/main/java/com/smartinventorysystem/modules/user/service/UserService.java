package com.smartinventorysystem.modules.user.service;

import com.smartinventorysystem.modules.auth.dto.response.AuthResponse;
import com.smartinventorysystem.modules.user.dto.Request.CreateStaffRequest;
import com.smartinventorysystem.modules.user.dto.Request.UpdateProfileRequest;
import com.smartinventorysystem.modules.user.dto.Response.CreateStaffResponse;

public interface UserService {
    AuthResponse updateProfile(Integer userId, UpdateProfileRequest request);
    void deleteAdmin(Integer adminId);
    void deleteStaff(Integer staffId);

    CreateStaffResponse createStaff(CreateStaffRequest request);
}
