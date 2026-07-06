package com.smartinventorysystem.modules.user.mapper;

import com.smartinventorysystem.modules.user.dto.Request.CreateStaffRequest;
import com.smartinventorysystem.modules.user.dto.Response.UserResponse;
import com.smartinventorysystem.modules.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // entity -> response
    @Mapping(source = "role", target = "role")
    @Mapping(source = "status", target = "status")
    UserResponse toResponse(User user);

    List<UserResponse> toResponseList(List<User> users);
}