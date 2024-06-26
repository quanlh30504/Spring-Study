package com.example.studySpring.Mapper;

import com.example.studySpring.DTOs.Request.RoleRequest;
import com.example.studySpring.DTOs.Response.RoleResponse;
import com.example.studySpring.Models.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);
//    @Mapping(target = "permissions", ignore = true)
    RoleResponse toRoleResponse(Role role);
}
