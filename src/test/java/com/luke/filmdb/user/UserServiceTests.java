package com.luke.filmdb.user;

import com.luke.filmdb.application.DTO.RegisterDTO;
import com.luke.filmdb.application.DTO.mapper.UserMapper;
import com.luke.filmdb.application.model.user.User;
import com.luke.filmdb.application.services.UserService;
import io.jsonwebtoken.lang.Assert;
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

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTests {

    private static final String ADMIN_USERNAME = "ADMIN_USERNAME";

    private static final String USER_PASSWORD = "USERNAME_TEST";
    private static final String USERNAME = "USERNAME_TEST";

    @Autowired
    UserService userService;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    UserMapper userMapper;

    private RegisterDTO getRegisterDTO() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setPassword(USER_PASSWORD);
        registerDTO.setUsername(USERNAME);
        return registerDTO;
    }

    @Test
    @WithMockUser(username = ADMIN_USERNAME, password = USER_PASSWORD, authorities = { "ADMIN", "USER" })
    public void updateUserWithMockAdminUser() {
        Mockito.when(bCryptPasswordEncoder.encode(Mockito.anyString())).thenReturn(USER_PASSWORD);
        Mockito.when(userMapper.userDtoToUser(getRegisterDTO())).thenReturn(new User(ADMIN_USERNAME, USER_PASSWORD, null));
        RegisterDTO registerDTO = getRegisterDTO();
        registerDTO.setUsername(ADMIN_USERNAME);
        User user = userService.saveUser(registerDTO);
        Assert.isTrue(user.getId() > 0);
    }

    @Test
//    @WithMockUser(username = USERNAME, password = USER_PASSWORD, authorities = { "ADMIN", "USER" })
//    @WithAnonymousUser
    @WithAnonymousUser
    public void saveNewUser() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
//        Mockito.when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(new Principal());
        User user = userService.saveUser(getRegisterDTO());
        Assert.isTrue(user.getId() > 0);
    }

}
