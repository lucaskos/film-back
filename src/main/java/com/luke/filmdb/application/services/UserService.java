package com.luke.filmdb.application.services;

import com.luke.filmdb.application.DTO.RegisterDTO;
import com.luke.filmdb.application.DTO.user.UserDTO;
import com.luke.filmdb.application.model.user.User;
import com.luke.filmdb.application.resource.filter.UserNotFoundException;

import java.util.Collection;
import java.util.List;

public interface UserService {
    User saveNewUser(RegisterDTO user);

    List<UserDTO> findAll();

    void delete(Long id);

    User findById(Long id);

    UserDTO update(UserDTO userDto);

    Collection<String> findLoggedUserRoles();

    User getCurrentlyLoggedUser() throws UserNotFoundException;

//    User findByUserName(String username) throws UserNotFoundException;
}
