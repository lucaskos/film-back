package com.example.demo.application.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserDTO extends LoginUserDTO{
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private boolean enabled;
    @NotNull
    private String email;
}
