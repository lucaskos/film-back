package com.example.demo.application.utils;

import com.example.demo.security.Roles;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class SecurityUtil {

    public User getCurrentlyLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return (User) authentication.getPrincipal();
    }

    public Collection<? extends GrantedAuthority> getCurrentlyLoggedAuthorities() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }

    public boolean hasUserAuthority(String role) {
        boolean hasRole;
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        hasRole = authorities.stream().anyMatch(a -> a.getAuthority().equalsIgnoreCase(role));

        return hasRole;
    }

}
