package com.smartinventorysystem.modules.user.service;

import com.smartinventorysystem.enums.Role;
import com.smartinventorysystem.modules.auth.dto.AuthResponse;
import com.smartinventorysystem.modules.auth.repository.UserRepository;
import com.smartinventorysystem.modules.user.dto.UpdateProfileRequest;
import com.smartinventorysystem.modules.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public AuthResponse updateProfile(Integer userId, UpdateProfileRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // update only allowed fields (
        if (request.getFullName() != null && !request.getFullName().isBlank()) {
            user.setFullName(request.getFullName());
        }

        if (request.getEmail() != null && !request.getEmail().isBlank()) {

            // prevent duplicate email
            boolean emailExists = userRepository.existsByEmail(request.getEmail());
            if (emailExists && !user.getEmail().equals(request.getEmail())) {
                throw new RuntimeException("Email already in use");
            }

            user.setEmail(request.getEmail());
        }

        user.setUpdatedAt(LocalDateTime.now());

        User updated = userRepository.save(user);

        return AuthResponse.builder()
                .userId(updated.getUserID())
                .fullName(updated.getFullName())
                .email(updated.getEmail())
                .role(updated.getRole().name())
                .message("Profile updated successfully")
                .build();
    }

    @Override
    public void deleteAdmin(Integer adminId) {

        User user = userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only admin accounts can be deleted.");
        }

        userRepository.delete(user);
    }

    @Override
    public void deleteStaff(Integer staffId) {

        User staff = userRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        if (staff.getRole() != Role.STAFF) {
            throw new RuntimeException("Only staff accounts can be deleted.");
        }

        userRepository.delete(staff);
    }

}
