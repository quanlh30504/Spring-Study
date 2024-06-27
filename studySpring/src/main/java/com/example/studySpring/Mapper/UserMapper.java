package com.example.studySpring.Mapper;

import com.example.studySpring.DTOs.Request.UserCreateRequest;
import com.example.studySpring.DTOs.Request.UserUpdateRequest;
import com.example.studySpring.DTOs.Response.UserResponse;
import com.example.studySpring.Models.User;
import jakarta.persistence.MapsId;
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
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "roles", ignore = true),
            @Mapping(target = "username", ignore = true)
    })
    User updateUser(UserUpdateRequest userUpdateRequest, @MappingTarget User user);
    @Mapping(source = "id",target = "id")
    UserResponse toUserResponse(User user);
}
