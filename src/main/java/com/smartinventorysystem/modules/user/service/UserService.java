package com.smartinventorysystem.modules.user.service;

import com.smartinventorysystem.modules.auth.dto.response.AuthResponse;
import com.smartinventorysystem.modules.user.dto.Request.CreateStaffRequest;
import com.smartinventorysystem.modules.user.dto.Request.UpdateProfileRequest;
import com.smartinventorysystem.modules.user.dto.Response.CreateStaffResponse;
import com.smartinventorysystem.modules.user.dto.Response.UserResponse;
import com.smartinventorysystem.modules.user.entity.User;

import java.util.List;

public interface UserService {
    AuthResponse updateProfile(Integer userId, UpdateProfileRequest request);
    void deleteAdmin(Integer adminId);
    void deleteStaff(Integer staffId);
    CreateStaffResponse createStaff(CreateStaffRequest request);
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Integer userId);
    void deactivateStaff(Integer staffId);
    void activateStaff(Integer userId);
}
