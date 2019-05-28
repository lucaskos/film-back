//package com.example.demo.config;
//
//import com.example.demo.application.services.UserServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
///**
// * Created by Luke on 24.10.2018.
// */
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SecurityConfig {
//    private UserServiceImpl service;
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public SecurityConfig(UserServiceImpl service, PasswordEncoder passwordEncoder){
//        this.service = service;
//        this.passwordEncoder = passwordEncoder;
//    }
//}
