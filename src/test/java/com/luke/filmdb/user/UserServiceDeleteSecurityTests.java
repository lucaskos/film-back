package com.luke.filmdb.user;


import com.luke.filmdb.application.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceDeleteSecurityTests {

    @Autowired
    private UserService userService;

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void deleteUser_AuthenticationCredentialsNotFoundException() {
        userService.delete(1L);
    }

//    @WithMockUser(username = "123", roles = {"USER"})
//    @Test
////            (expected = AuthenticationServiceException.class)
//    public void deleteUserWithAuthenticatedUser_Exception() {
//        userService.delete(1L);
//    }
//
//    @WithMockUser(authorities = "ROLE_ADMIN")
////    @Test
//    public void deleteWithAutheticatedAdmin() {
//        userService.delete(1L);
//    }
}
