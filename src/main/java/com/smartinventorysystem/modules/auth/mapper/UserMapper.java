package com.smartinventorysystem.modules.auth.mapper;

import com.smartinventorysystem.modules.auth.dto.SignupRequest;
import com.smartinventorysystem.modules.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(SignupRequest request) {
        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        return user;
    }
}
