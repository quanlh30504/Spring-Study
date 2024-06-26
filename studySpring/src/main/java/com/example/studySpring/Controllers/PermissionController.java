package com.example.studySpring.Controllers;

import com.example.studySpring.DTOs.Request.PermissionRequest;
import com.example.studySpring.DTOs.Response.ApiResponse;
import com.example.studySpring.DTOs.Response.PermissionResponse;
import com.example.studySpring.Service.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/permissions")
@RequiredArgsConstructor
@Slf4j
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping
    private ApiResponse<PermissionResponse> createPermission(@RequestBody PermissionRequest request){
        return ApiResponse.<PermissionResponse>builder()
                .code(1000)
                .message("Create permission successfully")
                .data(permissionService.createPermission(request))
                .build();
    }

    @GetMapping
    private ApiResponse<List<PermissionResponse>> getAllPermission(){
        return ApiResponse.<List<PermissionResponse>>builder()
                .code(1000)
                .message("Get all permissions.")
                .data(permissionService.getAllPermission())
                .build();
    }

    @DeleteMapping("/{name}")
    private ApiResponse delete(@PathVariable String name){
        permissionService.deletePermission(name);
        return ApiResponse.builder()
                .code(1000)
                .message("Delete successfully")
                .build();
    }
}
