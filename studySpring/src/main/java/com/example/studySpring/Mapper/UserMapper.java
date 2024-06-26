package com.example.studySpring.Mapper;

import com.example.studySpring.DTOs.Request.UserCreateRequest;
import com.example.studySpring.DTOs.Response.UserResponse;
import com.example.studySpring.Models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.UUID;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    User toUser(UserCreateRequest request);
    @Mapping(target = "id", ignore = true)
    User updateUser(@MappingTarget User user, UserCreateRequest userCreateRequest);
    @Mapping(source = "id",target = "id")
    UserResponse toUserResponse(User user);
}
