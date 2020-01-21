package com.luke.filmdb.security.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static com.luke.filmdb.config.SecurityConstants.TOKEN_PREFIX;

@Getter
@Setter
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
