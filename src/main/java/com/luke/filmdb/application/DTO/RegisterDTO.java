package com.luke.filmdb.application.DTO;

import com.luke.filmdb.application.DTO.user.UserDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO extends UserDTO {
    private String password;
}
