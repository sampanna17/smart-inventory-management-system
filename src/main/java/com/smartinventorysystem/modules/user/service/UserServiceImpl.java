package com.smartinventorysystem.modules.user.service;

import com.smartinventorysystem.enums.Role;
import com.smartinventorysystem.enums.Status;
import com.smartinventorysystem.exceptions.BadRequestException;
import com.smartinventorysystem.exceptions.ResourceNotFoundException;
import com.smartinventorysystem.modules.auth.dto.response.AuthResponse;
import com.smartinventorysystem.modules.email.service.EmailService;
import com.smartinventorysystem.modules.user.dto.Request.CreateStaffRequest;
import com.smartinventorysystem.modules.user.dto.Response.CreateStaffResponse;
import com.smartinventorysystem.modules.user.repository.UserRepository;
import com.smartinventorysystem.modules.user.dto.Request.UpdateProfileRequest;
import com.smartinventorysystem.modules.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    public AuthResponse updateProfile(Integer userId, UpdateProfileRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // update only allowed fields (
        if (request.getFullName() != null && !request.getFullName().isBlank()) {
            user.setFullName(request.getFullName());
        }

        if (request.getEmail() != null && !request.getEmail().isBlank()) {

            // prevent duplicate email
            boolean emailExists = userRepository.existsByEmail(request.getEmail());
            if (emailExists) {
                throw new BadRequestException("Email already in use");
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
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getRole() != Role.ADMIN) {
            throw new BadRequestException("Only admin accounts can be deleted.");
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

    @Override
    public CreateStaffResponse createStaff(CreateStaffRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        String temporaryPassword = generateTemporaryPassword();

        User staff = new User();
        staff.setFullName(request.getFullName());
        staff.setEmail(request.getEmail());
        staff.setPasswordHash(passwordEncoder.encode(temporaryPassword));
        staff.setRole(Role.STAFF);
        staff.setStatus(Status.ACTIVE);
        staff.setCreatedAt(LocalDateTime.now());

        User savedStaff = userRepository.save(staff);

        emailService.sendStaffAccountCreatedEmail(
                savedStaff.getEmail(),
                savedStaff.getFullName(),
                temporaryPassword
        );

        return CreateStaffResponse.builder()
                .userId(savedStaff.getUserID())
                .fullName(savedStaff.getFullName())
                .email(savedStaff.getEmail())
                .temporaryPassword(temporaryPassword)
                .role(savedStaff.getRole().name())
                .build();
    }

    private String generateTemporaryPassword() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }

}
