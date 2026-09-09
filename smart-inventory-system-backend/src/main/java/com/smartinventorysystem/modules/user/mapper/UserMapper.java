package com.smartinventorysystem.modules.user.mapper;

import com.smartinventorysystem.modules.user.dto.response.CreateStaffResponse;
import com.smartinventorysystem.modules.user.dto.response.UserResponse;
import com.smartinventorysystem.modules.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponse(User user);

    List<UserResponse> toResponseList(List<User> users);

    @Mapping(source = "userID", target = "userId")
    CreateStaffResponse toCreateStaffResponse(User user);
}