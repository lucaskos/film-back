package com.example.demo.application.services;

import com.example.demo.application.DTO.RegisterDTO;
import com.example.demo.application.DTO.UserDTO;
import com.example.demo.application.model.user.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
    User saveUser(RegisterDTO user);
    List<UserDTO> findAll();
    void delete(Long id);

    User findOne(String username);

    User findById(Long id);

    UserDTO update(UserDTO userDto);

    Collection<String> findLoggedUserRoles();

}
