package com.luke.filmdb.user;

import com.luke.filmdb.application.DTO.RegisterDTO;
import com.luke.filmdb.application.DTO.mapper.UserMapper;
import com.luke.filmdb.application.DTO.user.UserDTO;
import com.luke.filmdb.application.model.user.Role;
import com.luke.filmdb.application.model.user.User;
import com.luke.filmdb.application.repository.RoleRepo;
import com.luke.filmdb.application.repository.UserRepository;
import com.luke.filmdb.application.resource.filter.UserNotFoundException;
import com.luke.filmdb.application.services.UserServiceImpl;
import com.luke.filmdb.commons.SecurityUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.luke.filmdb.commons.UserCommons.ADMIN_USERNAME;
import static com.luke.filmdb.commons.UserCommons.ROLE_USER;
import static com.luke.filmdb.commons.UserCommons.ROLE_USER_ID;
import static com.luke.filmdb.commons.UserCommons.USERNAME;
import static com.luke.filmdb.commons.UserCommons.USERNAME_CHANGED_TEST;
import static com.luke.filmdb.commons.UserCommons.USER_ID;
import static com.luke.filmdb.commons.UserCommons.USER_PASSWORD;
import static com.luke.filmdb.commons.UserCommons.getRegisterDTO;
import static com.luke.filmdb.commons.UserCommons.getUser;
import static com.luke.filmdb.commons.UserCommons.getUserDTO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTests {

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    UserMapper userMapper;
    @Mock
    UserRepository userRepository;
    @Mock
    SecurityUtil securityUtil;
    @Mock
    RoleRepo roleRepo;
    @InjectMocks
    UserServiceImpl userService;


    @Test
    public void updateUserWithMockAdminUser() {
        when(bCryptPasswordEncoder.encode(Mockito.anyString())).thenReturn(USER_PASSWORD);
        when(userMapper.userDtoToUser(any())).thenReturn(new User(ADMIN_USERNAME, USER_PASSWORD, null));
        when(userRepository.save(any(User.class))).thenReturn(getUser());

        org.springframework.security.core.userdetails.User user =
                new org.springframework.security.core.userdetails.User(ADMIN_USERNAME, USER_PASSWORD, new ArrayList<>());
        when(securityUtil.getCurrentlyLoggedUser()).thenReturn(user);

        RegisterDTO registerDTO = getRegisterDTO();
        registerDTO.setUsername(ADMIN_USERNAME);

        when(roleRepo.findRoleByRoleName(any())).thenReturn(Role.getRoleWithNameAndId(ROLE_USER_ID, ROLE_USER));

        User userUpdated = userService.saveNewUser(registerDTO);

        Assert.assertNotNull(userUpdated);
        verify(userRepository, times(1)).save(any());
        verify(roleRepo, times(1)).findRoleByRoleName(ROLE_USER);
    }

    @Test
    public void saveNewUser() {

        User userToBeSaved = getUser();

        when(userRepository.findById(userToBeSaved.getId())).thenReturn(Optional.of(userToBeSaved));

        UserDTO user = userService.update(getRegisterDTO());

        Assert.assertNotNull(user);

        verify(userRepository, times(1)).save(userToBeSaved);
        verify(userRepository, times(1)).findById(userToBeSaved.getId());
    }

    @Test
    @WithMockUser(username = USERNAME, password = USER_PASSWORD, authorities = {"ADMIN", "USER"})
    public void updateUser() {
        UserDTO userDTO = getUserDTO();
        userDTO.setUsername(USERNAME_CHANGED_TEST);

        when(userRepository.findById(any())).thenReturn(Optional.of(getUser()));

        UserDTO update = userService.update(userDTO);
        Assert.assertTrue("Username equals changed value.", update.getUsername().equals(USERNAME_CHANGED_TEST));
    }

    @Test(expected = AuthorizationServiceException.class)
    public void getUserThrowAuthorizationServiceException() throws UserNotFoundException {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        userService.getCurrentlyLoggedUser();
    }

    @Test
    public void getCurrentlyLoggedUsernameAndCheckName() throws UserNotFoundException {
        org.springframework.security.core.userdetails.User user =
                new org.springframework.security.core.userdetails.User(ADMIN_USERNAME, USER_PASSWORD, new ArrayList<>());
        when(securityUtil.getCurrentlyLoggedUser()).thenReturn(user);

        User simpleUser = getUser();

        when(userRepository.findByUsername(ADMIN_USERNAME)).thenReturn(Optional.ofNullable(simpleUser));

        User currentlyLoggedUser = userService.getCurrentlyLoggedUser();

        Assert.assertEquals(USERNAME, currentlyLoggedUser.getUsername());
        verify(userRepository, times(1)).findByUsername(ADMIN_USERNAME);

    }

    @Test
    @WithMockUser(username = USERNAME, password = USER_PASSWORD, authorities = {"ADMIN", "USER"})
    public void getCurrentlyLoggedUsersRoles() {
        Collection<String> loggedUserRoles = userService.findLoggedUserRoles();
        Assert.assertNotNull(loggedUserRoles);
        Assert.assertEquals(2, loggedUserRoles.size());
    }

    @Test
    @WithMockUser(username = USERNAME, password = USER_PASSWORD, authorities = {"ADMIN", "USER"})
    public void findUserAndDeleteUser() {
        org.springframework.security.core.userdetails.User user =
                new org.springframework.security.core.userdetails.User(USERNAME, USER_PASSWORD, new ArrayList<>());
        when(securityUtil.getCurrentlyLoggedUser()).thenReturn(user);
        when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(getUser()));

        userService.delete(USER_ID);

        verify(userRepository, times(1)).delete(getUser());
    }

    @Test(expected = AuthenticationServiceException.class)
    @WithAnonymousUser
    public void findUserAndDelete_throwAuthenticationServiceException() {
        userService.delete(USER_ID);
        verify(userRepository, times(1)).delete(getUser());
    }

    @Test
    public void findAllUsers() {

        when(userRepository.findAll()).thenReturn(Collections.singletonList(getUser()));

        List<UserDTO> allUsers = userService.findAll();
        Assert.assertTrue(allUsers.size() > 0);
    }


}
