package com.luke.filmdb.commons;

import com.luke.filmdb.application.model.user.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class SecurityUtil {
    private final static String ROLE_PREFIX = "ROLE";

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

    private Collection<? extends GrantedAuthority> mapAuthoritiesFromRoles(Collection<Role> roles) {

        List<GrantedAuthority> authorities = new ArrayList<>();

        roles.forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName().replace(ROLE_PREFIX, "")));
            role.getPrivileges().stream()
                    .map(p -> new SimpleGrantedAuthority(p.getName()))
                    .forEach(authorities::add);
        });

        return authorities;
    }

}
