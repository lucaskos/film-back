package com.luke.filmdb.mapper;

import com.luke.filmdb.application.DTO.mapper.UserMapper;
import com.luke.filmdb.application.DTO.user.UserDTO;
import com.luke.filmdb.application.model.user.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.luke.filmdb.commons.UserCommons.getUserAndAdminRoles;
import static com.luke.filmdb.commons.UserCommons.getUser;
import static com.luke.filmdb.commons.UserCommons.getUserAndAdminRolesDto;
import static com.luke.filmdb.commons.UserCommons.getUserDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void userToUserDTOTest() {
        User user = getUser();
        user.setRoles(getUserAndAdminRoles());

        Assert.assertEquals(2, user.getRoles().size());

        UserDTO userDTO = userMapper.userToUserDto(user);

        Assert.assertEquals(user.getUsername(), userDTO.getUsername());
        Assert.assertEquals(user.getId(), userDTO.getId());
        Assert.assertEquals(user.getEmail(), userDTO.getEmail());
        Assert.assertEquals(user.getLastName(), userDTO.getLastName());
        Assert.assertEquals(user.getFirstName(), userDTO.getFirstName());

        Assert.assertEquals(2, userDTO.getRoles().size());
        Assert.assertEquals(user.getRoles().get(0).getRoleName(), userDTO.getRoles().get(0).getRoleName());
        Assert.assertEquals(user.getRoles().get(0).getId(), userDTO.getRoles().get(0).getId());
    }

    @Test
    public void userDtoToUserTest() {
        UserDTO userDTO = getUserDTO();
        userDTO.setRoles(getUserAndAdminRolesDto());

        Assert.assertEquals(2, userDTO.getRoles().size());

        User user = userMapper.userDtoToUser(userDTO);

        Assert.assertEquals(userDTO.getUsername(), user.getUsername());
        Assert.assertEquals(userDTO.getId(), user.getId());
        Assert.assertEquals(userDTO.getEmail(), user.getEmail());
        Assert.assertEquals(userDTO.getLastName(), user.getLastName());
        Assert.assertEquals(userDTO.getFirstName(), user.getFirstName());

        Assert.assertEquals(2, userDTO.getRoles().size());
        Assert.assertEquals(userDTO.getRoles().get(0).getRoleName(), user.getRoles().get(0).getRoleName());
        Assert.assertEquals(userDTO.getRoles().get(0).getId(), user.getRoles().get(0).getId());

    }

}
