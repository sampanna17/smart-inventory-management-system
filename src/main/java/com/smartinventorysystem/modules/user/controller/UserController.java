package com.smartinventorysystem.modules.user.controller;

import com.smartinventorysystem.common.dto.ApiResponse;
import com.smartinventorysystem.constants.ApiRoutes;
import com.smartinventorysystem.modules.auth.dto.response.AuthResponse;
import com.smartinventorysystem.modules.user.dto.Request.CreateStaffRequest;
import com.smartinventorysystem.modules.user.dto.Request.UpdateProfileRequest;
import com.smartinventorysystem.modules.user.dto.Response.CreateStaffResponse;
import com.smartinventorysystem.modules.user.entity.User;
import com.smartinventorysystem.modules.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(ApiRoutes.Users.BASE)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PatchMapping(ApiRoutes.Users.UPDATE_PROFILE)
    public ResponseEntity<ApiResponse<AuthResponse>> updateProfile(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody UpdateProfileRequest request) {

        AuthResponse response = userService.updateProfile(user.getUserID(), request);
        return ResponseEntity.ok(
                ApiResponse.<AuthResponse>builder()
                        .success(true)
                        .message(response.getMessage())
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @DeleteMapping(ApiRoutes.Users.DELETE_ADMIN)
    public ResponseEntity<ApiResponse<Void>> deleteAdmin(@PathVariable Integer adminId) {

        userService.deleteAdmin(adminId);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Admin deleted successfully")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @DeleteMapping(ApiRoutes.Users.DELETE_STAFF)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteStaff(@PathVariable Integer staffId) {

        userService.deleteStaff(staffId);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Staff deleted successfully")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PostMapping(ApiRoutes.Users.CREATE_STAFF)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CreateStaffResponse>> createStaff(
            @Valid @RequestBody CreateStaffRequest request) {

        CreateStaffResponse response = userService.createStaff(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<CreateStaffResponse>builder()
                        .success(true)
                        .message("Staff created successfully")
                        .data(response)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}

