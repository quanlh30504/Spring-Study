package com.example.studySpring.Service;

import com.example.studySpring.DTOs.Request.UserCreateRequest;
import com.example.studySpring.DTOs.Response.UserResponse;
import com.example.studySpring.Enums.Role;
import com.example.studySpring.ExceptionHandling.AppException;
import com.example.studySpring.ExceptionHandling.ErrorCode;
import com.example.studySpring.Mapper.UserMapper;
import com.example.studySpring.Models.User;
import com.example.studySpring.Repository.UserRepository;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

//    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUser(){
        List<User> users =  userRepository.findAll();
        List<UserResponse> userResponseList = users.stream()
                .map(user -> {
                    UserResponse userResponse = userMapper.toUserResponse(user);
                    return userResponse;
                })
                .toList();
        return userResponseList;
    }

    @Transactional
    public UserResponse createUser(UserCreateRequest newUser) {
        if (userRepository.existsByUsername(newUser.getUsername())) {
            log.info("User with username {} already exists", newUser.getUsername());
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        // Dùng MapStruct chuyển từ UserCreateRequest sang User
        User user = userMapper.toUser(newUser);
        user.setPassword(passwordEncoder.encode(newUser.getPassword())); // Mã hóa password

        Set<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }


    // Hàm chỉ trả về kết quả khi user request chính là user trả về hoặc user đó có role ADMIN
//    @PostAuthorize("returnObject.owner == authentication.name or hasRole('ADMIN')")
    public UserResponse getUserById(String id){
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_ID_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

//    @PostAuthorize("returnObject.owner == authentication.name")
    public UserResponse getMyInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }


}
