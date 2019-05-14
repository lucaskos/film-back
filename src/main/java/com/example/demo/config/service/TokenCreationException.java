package com.example.demo.config.service;

import com.auth0.jwt.exceptions.JWTCreationException;

/**
 * Created by Luke on 24.10.2018.
 */
public class TokenCreationException extends Throwable {
    public TokenCreationException(String s, JWTCreationException ex) {
    }
}
