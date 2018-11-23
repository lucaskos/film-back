package com.example.demo.application.DTO.mapper;

import com.example.demo.application.DTO.UserDTO;
import com.example.demo.application.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO userToUserDto(User user);

    User userDtoToUser(UserDTO userDTO);
}
