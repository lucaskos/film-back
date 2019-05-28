package com.example.demo.application.DTO.mapper;

import com.example.demo.application.DTO.RegisterDTO;
import com.example.demo.application.DTO.UserDTO;
import com.example.demo.application.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO userToUserDto(User user);

    User userDtoToUser(UserDTO userDTO);

//    @Mapping(source = "user.id", target = "userDto.id", qualifiedByName = "hideUserDetails")
//    UserDTO userToHideDetailsDTO(User user);

    @Named("hideUserDetails")
    default UserDTO hideUserDetails(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setUsername(user.getUsername());
        return userDTO;
    }
}
