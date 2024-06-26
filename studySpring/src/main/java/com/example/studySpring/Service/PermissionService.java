package com.example.studySpring.Service;

import com.example.studySpring.DTOs.Request.PermissionRequest;
import com.example.studySpring.DTOs.Response.PermissionResponse;
import com.example.studySpring.Mapper.PermissionMapper;
import com.example.studySpring.Models.Permission;
import com.example.studySpring.Repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    public PermissionResponse createPermission(PermissionRequest request){
        Permission permission = permissionMapper.toPermission(request);
        permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAllPermission(){
        List<Permission> permissionList = permissionRepository.findAll();
        return permissionList.stream()
                .map(permissionMapper::toPermissionResponse)
                .toList();
    }

    public void deletePermission(String permission){
        permissionRepository.deleteById(permission);
    }


}
