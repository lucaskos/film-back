package com.example.demo;

import com.example.demo.application.model.user.Role;
import com.example.demo.application.model.user.User;
import com.example.demo.application.repository.UserRepository;
import com.example.demo.security.jwt.TokenProvider;
import io.jsonwebtoken.lang.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TokenProviderAuthenticationTest {

    private static final String USER_NAME = "TEST";
    private static final String USER_ROLE_NAME = "ROLE_TEST";

    /* "TEST" password */
    private final static String ENCODED_PASSWORD = "$2a$10$EU.Q.2fkmnHvsuq/H4aSGenywkxX7NFmU2CIcKcL.SIAlX62N0YFK";

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @MockBean
    UserRepository userRepository;

    @Test
    public void generateToken() {
        Role role = new Role();
        role.setRole(USER_ROLE_NAME);
        User user = new User(USER_NAME, ENCODED_PASSWORD, Arrays.asList(role));
        Optional<User> userOptional = Optional.of(user);

        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(userOptional);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("TEST", "TEST"));

        Assert.notNull(authentication);
        Assert.isTrue(authentication.getName().equals(USER_NAME));

        String tokenGenerated = tokenProvider.generateToken(authentication);
        Assert.notNull(tokenGenerated);

    }
}
