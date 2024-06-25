package com.example.studySpring.Config;

import com.example.studySpring.Models.Role;
import com.example.studySpring.Models.User;
import com.example.studySpring.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

//    private UserService userService;
//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager(){
//        User user1 = User.builder()
//                .fullName("Nguyễn Văn Quân")
//                .phoneNumber("08432451")
//                .address("Ha noi")
//                .username("quannguyen")
//                .password("quannguyen").build();
//        user1 = userService.createUserRole(1, user1);
//        return new InMemoryUserDetailsManager(user1);
//    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
