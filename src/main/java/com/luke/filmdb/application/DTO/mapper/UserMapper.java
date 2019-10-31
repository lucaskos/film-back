package com.luke.filmdb.application.DTO.mapper;

import com.luke.filmdb.application.DTO.LoginUserDTO;
import com.luke.filmdb.application.DTO.UserDTO;
import com.luke.filmdb.application.model.user.User;
import org.mapstruct.Mapper;
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

    LoginUserDTO userToLoginUserDTO(User user);
}
