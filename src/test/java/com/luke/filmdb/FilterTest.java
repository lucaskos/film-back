package com.luke.filmdb;

import com.luke.filmdb.security.AuthoritiesConstants;
import com.luke.filmdb.security.jwt.JWTAuthorizationFilter;
import com.luke.filmdb.security.jwt.TokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {JWTAuthorizationFilter.class})
public class FilterTest {

    @MockBean
    UserDetailsService userDetailsService;
    @MockBean
    AuthenticationManager authManager;
    @InjectMocks
    TokenProvider tokenProvider;

    final String OLD_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0LXVzZXIiLCJzY29wZXMiOiJST0xFX1VTRVIiLCJpYXQiOjE1ODA2NjU0NzgsImV4cCI6MTU4MDY4MzQ3OH0.YhsVpvYMCmuNtLwv0MUT6jJwbfNpoBggWarVvcqd4k0";
    String username = "test-user";
    String password = "test-password";
    String freshToken;

    @Before
    public void init() {
        freshToken = this.tokenProvider.generateToken(new UsernamePasswordAuthenticationToken(
                this.username,
                this.password,
                Collections.singletonList(new SimpleGrantedAuthority(AuthoritiesConstants.USER))));
    }

    @Test
    public void generateTokenAssertNotNull() {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                this.username,
                this.password,
                Collections.singletonList(new SimpleGrantedAuthority(AuthoritiesConstants.USER))
        );

        String token = this.tokenProvider.generateToken(authentication);

        assertNotNull(token);

        assertEquals(this.freshToken, token);
    }

    @Test(expected = ExpiredJwtException.class)
    public void getTokenExpired() {
        String usernameFromToken = this.tokenProvider.getUsernameFromToken(OLD_TOKEN);

        assertEquals(this.username, usernameFromToken);
    }

    @Test
    public void getUsernameFromTokenAndAssertEqual() {
        String usernameFromToken = this.tokenProvider.getUsernameFromToken(freshToken);

        assertEquals(this.username, usernameFromToken);
    }
}
