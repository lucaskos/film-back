package com.example.demo.security.model;

import lombok.Data;

@Data
public class AuthToken {

    private String token;
    private String username;
}