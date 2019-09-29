package com.example.demo.security.model;

import lombok.Data;

import java.util.List;

import static com.example.demo.config.SecurityConstants.TOKEN_PREFIX;

@Data
public class AuthToken {

    private String token;
    private String username;
    private List<String> roles;

    public AuthToken(String token, String username, List<String> roles) {
        this.token = TOKEN_PREFIX + token;
        this.username = username;
        this.roles = roles;
    }
}