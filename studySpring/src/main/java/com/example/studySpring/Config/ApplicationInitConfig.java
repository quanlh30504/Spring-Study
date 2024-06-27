package com.example.studySpring.Config;

import com.example.studySpring.Enums.Roles;
import com.example.studySpring.ExceptionHandling.AppException;
import com.example.studySpring.ExceptionHandling.ErrorCode;
import com.example.studySpring.Models.Role;
import com.example.studySpring.Models.User;
import com.example.studySpring.Repository.RoleRepository;
import com.example.studySpring.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    // Config tạo 1 user với role admin khi chạy ứng dụng lần đầu
    @Bean
    ApplicationRunner applicationRunner (UserRepository userRepository){
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
//                Role role = roleRepository.findById("ADMIN")
//                        .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
//                log.info(role.toString());
                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
//                        .roles(new HashSet<>(List.of(role)))
                        .build();

                userRepository.save(user);
                log.warn("admin user has been created with default password: admin, please change password !");
            }
        };
    }

}
