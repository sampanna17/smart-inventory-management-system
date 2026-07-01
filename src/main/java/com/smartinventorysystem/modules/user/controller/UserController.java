package com.smartinventorysystem.modules.user.controller;

import com.smartinventorysystem.modules.auth.dto.AuthResponse;
import com.smartinventorysystem.modules.user.dto.UpdateProfileRequest;
import com.smartinventorysystem.modules.user.entity.User;
import com.smartinventorysystem.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("/update-profile")
    public ResponseEntity<AuthResponse> updateProfile(
            @AuthenticationPrincipal User user,
            @RequestBody UpdateProfileRequest request) {

        return ResponseEntity.ok(
                userService.updateProfile(user.getUserID(), request)
        );
    }

    @DeleteMapping("/admin/{adminId}")
    public ResponseEntity<String> deleteAdmin(@PathVariable Integer adminId) {

        userService.deleteAdmin(adminId);

        return ResponseEntity.ok("Admin deleted successfully");
    }

    @DeleteMapping("/staff/{staffId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteStaff(@PathVariable Integer staffId) {

        userService.deleteStaff(staffId);

        return ResponseEntity.ok("Staff deleted successfully");
    }
}
