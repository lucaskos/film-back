package com.luke.filmdb.application.DTO;

import com.luke.filmdb.application.DTO.user.UserDTO;
import lombok.Data;

@Data
public class RegisterDTO extends UserDTO {
    private String password;
}
