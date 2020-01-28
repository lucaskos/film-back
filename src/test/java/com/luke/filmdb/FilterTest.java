package com.luke.filmdb;

import com.luke.filmdb.config.SecurityConstants;
import com.luke.filmdb.security.jwt.JWTAuthorizationFilter;
import com.luke.filmdb.security.jwt.TokenProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.luke.filmdb.config.SecurityConstants.HEADER_STRING;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class FilterTest {

    @Mock
    UserDetailsService userDetailsService;
    @Mock
    AuthenticationManager authManager;
    @Autowired
    TokenProvider tokenProvider;

    @InjectMocks
    JWTAuthorizationFilter filterUnderTest;

    String token;

    @Before
    public void generateToken() {
        Authentication authentication = Mockito.mock(Authentication.class);
        authentication.setAuthenticated(true);
        token = tokenProvider.generateToken(authentication);
    }

    @Test
    public void testSomethingAboutDoFilter() throws ServletException, IOException {
        // create the objects to be mocked
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        // mock the getRequestURI() response
        when(httpServletRequest.getRequestURI()).thenReturn("/otherurl.jsp");
        when(httpServletRequest.getHeader(HEADER_STRING)).thenReturn(SecurityConstants.TOKEN_PREFIX + "test");

        filterUnderTest.doFilter(httpServletRequest, httpServletResponse,
                filterChain);

        // verify if a sendRedirect() was performed with the expected value
        verify(httpServletResponse).sendRedirect("/maintenance.jsp");
    }
}
