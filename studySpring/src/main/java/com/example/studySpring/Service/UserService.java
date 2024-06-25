package com.example.studySpring.Service;

import com.example.studySpring.DTOs.Request.UserCreateRequest;
import com.example.studySpring.DTOs.Response.UserResponse;
import com.example.studySpring.ExceptionHandling.AppException;
import com.example.studySpring.ExceptionHandling.ErrorCode;
import com.example.studySpring.Mapper.UserMapper;
import com.example.studySpring.Models.User;
import com.example.studySpring.Repository.RoleRepository;
import com.example.studySpring.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    @Autowired
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private Logger logger = LoggerFactory.getLogger(UserService.class);

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

//    public UserCreateRequest createUser(UserCreateRequest newUser){
//        logger.info("Aloooo");
//        if (userRepository.existsByUsername(newUser.getUsername())){
//            throw new AppException(ErrorCode.USER_EXISTED);
//        }else{
//            String passwordEndcode = passwordEncoder.encode(newUser.getPassword());
//
//            // Dùng Mapstruct chuyển từ UserCreateRequest sang User
//            User user = UserMapper.INSTANCE.toUser(newUser);
//            user.setPassword(passwordEndcode);
//            userRepository.save(user);
//            return newUser;
//        }
//    }
    @Transactional
    public UserCreateRequest createUser(UserCreateRequest newUser) {
        if (userRepository.existsByUsername(newUser.getUsername())) {
            logger.info("User with username {} already exists", newUser.getUsername());
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        String encodedPassword = passwordEncoder.encode(newUser.getPassword());

        // Dùng MapStruct chuyển từ UserCreateRequest sang User
        User user = userMapper.toUser(newUser);
        user.setPassword(encodedPassword);
        userRepository.save(user);
        logger.info(user.toString());
        return newUser;

    }

    public UserResponse getUserById(int id){
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_ID_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

}
