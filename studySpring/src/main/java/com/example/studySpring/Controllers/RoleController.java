package com.example.studySpring.Controllers;

import com.example.studySpring.DTOs.Request.PermissionRequest;
import com.example.studySpring.DTOs.Request.RoleRequest;
import com.example.studySpring.DTOs.Response.ApiResponse;
import com.example.studySpring.DTOs.Response.PermissionResponse;
import com.example.studySpring.DTOs.Response.RoleResponse;
import com.example.studySpring.Service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@Slf4j
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    private ApiResponse<RoleResponse> createPermission(@RequestBody RoleRequest request){
        return ApiResponse.<RoleResponse>builder()
                .code(1000)
                .message("Create permission successfully")
                .data(roleService.createRole(request))
                .build();
    }

    @GetMapping
    private ApiResponse<List<RoleResponse>> getAllPermission(){
        return ApiResponse.<List<RoleResponse>>builder()
                .code(1000)
                .message("Get all permissions.")
                .data(roleService.getAllRole())
                .build();
    }

    @DeleteMapping("/{role}")
    private ApiResponse delete(@PathVariable String role){
        roleService.deleteRole(role);
        return ApiResponse.builder()
                .code(1000)
                .message("Delete successfully")
                .build();
    }
}
