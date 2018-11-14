package com.example.demo.application.dto.mapper;

import com.example.demo.application.dto.UserDTO;
import com.example.demo.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO userDtoToUser(User user);
}
