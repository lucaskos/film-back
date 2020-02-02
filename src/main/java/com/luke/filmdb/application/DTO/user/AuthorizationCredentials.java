package com.luke.filmdb.application.DTO.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorizationCredentials {
    private String username;
    private String password;
}
