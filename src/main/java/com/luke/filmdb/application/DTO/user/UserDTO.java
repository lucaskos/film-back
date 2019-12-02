package com.luke.filmdb.application.DTO.user;

import com.luke.filmdb.application.DTO.user.LoginUserDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserDTO extends LoginUserDTO {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private boolean enabled;
    @NotNull
    private String email;
}
