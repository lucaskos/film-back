package com.luke.filmdb.application.DTO.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class UserDTO extends LoginUserDTO {
    private String firstName;
    private String lastName;
    private boolean enabled;
    private String email;
    private List<RoleDTO> roles;
}
