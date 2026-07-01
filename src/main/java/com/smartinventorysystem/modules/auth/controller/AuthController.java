package com.smartinventorysystem.modules.auth.controller;

import com.smartinventorysystem.modules.auth.dto.AuthResponse;
import com.smartinventorysystem.modules.auth.dto.LoginRequest;
import com.smartinventorysystem.modules.auth.dto.SignupRequest;
import com.smartinventorysystem.modules.auth.service.AuthService;
import com.smartinventorysystem.modules.user.dto.UpdateProfileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody SignupRequest request) {
        return ResponseEntity.ok(authService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PutMapping("/profile/{userId}")
    public ResponseEntity<AuthResponse> updateProfile(
            @PathVariable Integer userId,
            @RequestBody UpdateProfileRequest request) {

        return ResponseEntity.ok(authService.updateProfile(userId, request));
    }

}
