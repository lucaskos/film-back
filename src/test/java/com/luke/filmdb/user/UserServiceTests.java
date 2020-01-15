package com.luke.filmdb.user;

import com.luke.filmdb.application.DTO.RegisterDTO;
import com.luke.filmdb.application.DTO.mapper.UserMapper;
import com.luke.filmdb.application.DTO.user.UserDTO;
import com.luke.filmdb.application.model.user.User;
import com.luke.filmdb.application.repository.UserRepository;
import com.luke.filmdb.application.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTests {

    private static final String ADMIN_USERNAME = "ADMIN_USERNAME";

    private static final String USER_PASSWORD = "USERNAME_TEST";
    private static final String USERNAME = "USERNAME_TEST";
    private static final String FIRSTNAME = "FIRSTNAME";
    private static final String USERNAME_CHANGED_TEST = "NEWUSERNAME";
    private static final Long USER_ID = 1L;

    @Autowired
    UserService userService;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    UserMapper userMapper;
    @Mock
    UserRepository userRepository;

    private RegisterDTO getRegisterDTO() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setPassword(USER_PASSWORD);
        registerDTO.setUsername(USERNAME);
        return registerDTO;
    }

    @Test
    @WithMockUser(username = ADMIN_USERNAME, password = USER_PASSWORD, authorities = {"ADMIN", "USER"})
    public void updateUserWithMockAdminUser() {
        Mockito.when(bCryptPasswordEncoder.encode(Mockito.anyString())).thenReturn(USER_PASSWORD);
        Mockito.when(userMapper.userDtoToUser(getRegisterDTO())).thenReturn(new User(ADMIN_USERNAME, USER_PASSWORD, null));
        RegisterDTO registerDTO = getRegisterDTO();
        registerDTO.setUsername(ADMIN_USERNAME);
        User user = userService.saveUser(registerDTO);
        Assert.isTrue(user.getId() > 0, "User has been updated.");
    }

    @Test
    @WithAnonymousUser
    public void saveNewUser() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        User user = userService.saveUser(getRegisterDTO());
        Assert.isTrue(user.getId() > 0, "New user saved.");
    }

    @Test
    @WithMockUser(username = USERNAME, password = USER_PASSWORD, authorities = {"ADMIN", "USER"})
    public void updateUser() {
        UserDTO userDTO = getUserDTO();
        Assert.isTrue(!userDTO.getUsername().equals(USERNAME), "UserDTO.username != USERNAME_TEST.");

        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(getUser()));

        UserDTO update = userService.update(userDTO);
        Assert.isTrue(update.getUsername().equals(USERNAME_CHANGED_TEST), "Username equals changed value.");
    }

    private UserDTO getUserDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(FIRSTNAME);
        userDTO.setUsername(USERNAME_CHANGED_TEST);
        userDTO.setId(USER_ID);
        return userDTO;
    }

    public User getUser() {
        User user = User.getInstance();
        user.setId(USER_ID);
        user.setUsername(USERNAME);
        return user;
    }

}
