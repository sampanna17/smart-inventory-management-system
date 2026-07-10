package com.smartinventorysystem.modules.auth.mapper;

import com.smartinventorysystem.modules.auth.dto.request.SignupRequest;
import com.smartinventorysystem.modules.auth.dto.response.AuthResponse;
import com.smartinventorysystem.modules.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthUserMapper  {

    // Map request DTO to entity
    User toEntity(SignupRequest request);

    @Mapping(source = "userID", target = "userId")
    AuthResponse toResponse(User user);
}
