package com.smartinventorysystem.modules.auth.controller;

import com.smartinventorysystem.modules.auth.dto.AuthResponse;
import com.smartinventorysystem.modules.auth.dto.LoginRequest;
import com.smartinventorysystem.modules.auth.dto.SignupRequest;
import com.smartinventorysystem.modules.auth.service.AuthService;
import com.smartinventorysystem.modules.user.dto.UpdateProfileRequest;
import com.smartinventorysystem.modules.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Missing token");
        }

        String token = authHeader.substring(7);

        authService.logout(token);

        return ResponseEntity.ok("Logged out successfully");
    }
}
