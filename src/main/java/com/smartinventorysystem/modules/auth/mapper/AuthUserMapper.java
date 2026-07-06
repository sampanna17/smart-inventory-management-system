package com.smartinventorysystem.modules.auth.mapper;

import com.smartinventorysystem.modules.auth.dto.request.SignupRequest;
import com.smartinventorysystem.modules.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthUserMapper  {
    User toEntity(SignupRequest request);
}
