package com.example.demo.config.service;

import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.config.UserNotFoundException;
import com.example.demo.repository.UserService;
import com.example.demo.config.model.TokenProperties;
import com.example.demo.config.model.TokenUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Luke on 24.10.2018.
 */
@Service
public class UsernamePasswordDetailsService implements UserDetailsService  {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private UserService userService;
    private TokenService tokenService;
    private Algorithm algorithm;

    @Autowired
    public UsernamePasswordDetailsService(TokenProperties tokenProperties, UserService userService, TokenService tokenService) throws UnsupportedEncodingException {
        this.algorithm = Algorithm.HMAC256(tokenProperties.getSecret());
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.debug("Trying to authenticate ", email);
        try {
            return (UserDetails) getUserDetails(userService.findByEmail(email));
        } catch (UserNotFoundException ex) {
            throw new UsernameNotFoundException("Account for '" + email + "' not found", ex);
        }
    }

    private TokenUserDetails getUserDetails(User user) {
        TokenUserDetails tokenUserDetails = new TokenUserDetails(
                user.getEmail(),
                user.getUsername(),
                user.getPassword(),
                tokenService.encode(user),
                user.isEnabled(),
                getAuthorities(user.getRoles()));
        return tokenUserDetails;
    }

    private List<SimpleGrantedAuthority> getAuthorities(List<Role> roles) {
        return roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());
    }
}
