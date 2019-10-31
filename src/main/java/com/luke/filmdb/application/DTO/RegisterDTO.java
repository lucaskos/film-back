package com.luke.filmdb.application.DTO;

import lombok.Data;

@Data
public class RegisterDTO extends UserDTO {
    private String password;
}
