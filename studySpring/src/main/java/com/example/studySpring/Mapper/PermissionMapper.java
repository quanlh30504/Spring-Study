package com.example.studySpring.Mapper;

import com.example.studySpring.DTOs.Request.PermissionRequest;
import com.example.studySpring.DTOs.Response.PermissionResponse;
import com.example.studySpring.Models.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    @Mappings({
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "description", target = "description")
    })
    PermissionResponse toPermissionResponse(Permission permission);
    @Mappings({
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "description", target = "description")
    })
    Permission toPermission(PermissionRequest request);

}
