package com.example.demo.security;

import com.example.demo.application.DTO.UserDTO;
import com.example.demo.application.model.User;

import java.util.List;

public interface UserService {
    User save(UserDTO user);
    List<User> findAll();
    void delete(Long id);

    User findOne(String username);

    User findById(Long id);

    UserDTO update(UserDTO userDto);
}
