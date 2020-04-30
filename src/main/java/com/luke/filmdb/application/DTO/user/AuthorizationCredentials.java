package com.luke.filmdb.application.DTO.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthorizationCredentials {
    private String username;
    private String password;
}
