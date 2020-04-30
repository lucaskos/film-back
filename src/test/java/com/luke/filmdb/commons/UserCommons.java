package com.luke.filmdb.commons;

import com.luke.filmdb.application.DTO.RegisterDTO;
import com.luke.filmdb.application.DTO.user.RoleDTO;
import com.luke.filmdb.application.DTO.user.UserDTO;
import com.luke.filmdb.application.model.user.Role;
import com.luke.filmdb.application.model.user.User;

import java.util.Arrays;
import java.util.List;

public class UserCommons {
    public static final String ADMIN_USERNAME = "ADMIN_USERNAME";
    public static final String USER_PASSWORD = "USERNAME_TEST";
    public static final String USERNAME = "USERNAME_TEST";
    public static final String FIRST_NAME = "FIRSTNAME";
    public static final String USERNAME_CHANGED_TEST = "NEWUSERNAME";
    public static final Long USER_ID = 1L;
    public static final String EMAIL_ADDRESS = "EMAIL_ADDRESS";
    public static final String TOKEN = "TOKEN";

    public static final String ROLE_USER = "ROLE_USER";
    public static final Long ROLE_USER_ID = 1L;
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final Long ROLE_ADMIN_ID = 2L;

    public static UserDTO getUserDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(FIRST_NAME);
        userDTO.setUsername(USERNAME);
        userDTO.setId(USER_ID);
        return userDTO;
    }

    public static User getUser() {
        User user = User.getInstance();
        user.setId(USER_ID);
        user.setUsername(USERNAME);
        return user;
    }

    public static RegisterDTO getRegisterDTO() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setPassword(USER_PASSWORD);
        registerDTO.setUsername(USERNAME);
        registerDTO.setId(USER_ID);
        return registerDTO;
    }

    public static List<Role> getUserAndAdminRoles() {
        return Arrays.asList(
                Role.getRoleWithNameAndId(ROLE_USER_ID, ROLE_USER),
                Role.getRoleWithNameAndId(ROLE_ADMIN_ID, ROLE_ADMIN));
    }

    public static List<RoleDTO> getUserAndAdminRolesDto() {
        return Arrays.asList(
                new RoleDTO(ROLE_USER_ID, ROLE_USER),
                new RoleDTO(ROLE_ADMIN_ID, ROLE_ADMIN));
    }
}
