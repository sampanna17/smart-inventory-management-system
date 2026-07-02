package com.smartinventorysystem.modules.auth.controller;

import com.smartinventorysystem.constants.ApiRoutes;
import com.smartinventorysystem.modules.auth.dto.response.AuthResponse;
import com.smartinventorysystem.modules.auth.dto.request.LoginRequest;
import com.smartinventorysystem.modules.auth.dto.request.SignupRequest;
import com.smartinventorysystem.modules.auth.service.AuthService;
import com.smartinventorysystem.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiRoutes.AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody SignupRequest request) {
        return ResponseEntity.ok(authService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) {
        authService.logout(jwtUtil.extractToken(authHeader));
        return ResponseEntity.ok("Logged out successfully");
    }
}
