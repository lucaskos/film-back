package com.luke.filmdb.application.services;

import com.luke.filmdb.application.DTO.RegisterDTO;
import com.luke.filmdb.application.DTO.mappers.UserMapper;
import com.luke.filmdb.application.DTO.user.UserDTO;
import com.luke.filmdb.application.model.user.Role;
import com.luke.filmdb.application.model.user.User;
import com.luke.filmdb.application.repositories.RoleRepo;
import com.luke.filmdb.application.repositories.UserRepository;
import com.luke.filmdb.application.resources.filter.UserNotFoundException;
import com.luke.filmdb.commons.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.luke.filmdb.security.Roles.ADMIN;
import static com.luke.filmdb.security.Roles.USER;

/**
 * Created by Luke on 24.10.2018.
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bcryptEncoder;
    private final UserMapper userMapper;
    private final RoleRepo roleRepository;
    private final SecurityUtils securityUtils;

    @Secured(ADMIN)
    public List<UserDTO> findAll() {
        List<UserDTO> list = new ArrayList<>();
        userRepository.findAll().stream().forEach(user -> {
            list.add(userMapper.userToUserDto(user));
        });
        return list;
    }

    @PreAuthorize("isAuthenticated()")
    @Secured(ADMIN)
    public void delete(Long id) {
        User userToRemove = findById(id);

        if (isActualUserLoggedOrAdmin(userToRemove)) {
            userRepository.delete(userToRemove);
        } else {
            throw new AuthenticationServiceException("You're cannot perform this operation");
        }
    }

    private boolean isActualUserLoggedOrAdmin(User loggedUser) {
        if (loggedUser == null || loggedUser.getUsername() == null)
            return false;

        if (this.securityUtils.getCurrentlyLoggedUser() == null)
            return false;

        String username = this.securityUtils.getCurrentlyLoggedUser().getUsername();
        boolean hasAdminAuthority = this.securityUtils.hasUserAuthority(ADMIN);

        return username.equals(loggedUser.getUsername()) || hasAdminAuthority;
    }

    public User findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }

    public UserDTO update(UserDTO UserDTO) {
        User user = findById(UserDTO.getId());

        if (user != null) {
            BeanUtils.copyProperties(UserDTO, user, "password");
            userRepository.save(user);
        }
        return UserDTO;
    }

    public Collection<String> findLoggedUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList()) : null;
    }

    public User saveNewUser(RegisterDTO user) {
        Role defaultRole = getDefaultRole();

        User userEntity = userMapper.userDtoToUser(user);

        userEntity.setRoles(Collections.singletonList(defaultRole));
        userEntity.setPassword(bcryptEncoder.encode(user.getPassword()));

        if (!isActualUserLoggedOrAdmin(userEntity) || user.getPassword() == null) {
            throw new AuthorizationServiceException("Error in authorization");
        }
        return userRepository.save(userEntity);
    }


    Role getDefaultRole() {
        Role role_user = roleRepository.findRoleByRoleName(USER);
        return role_user;
    }

//    public User findByEmail(String email) throws UserNotFoundException {
//        return userDao.findByEmail(email).orElseThrow(UserNotFoundException::new);
//    }
//
//    public User findByUserName(String username) throws UserNotFoundException {
//        return userDao.findByUsername(username).orElseThrow(UserNotFoundException::new);
//    }

    public User getCurrentlyLoggedUser() throws UserNotFoundException {
        org.springframework.security.core.userdetails.User currentlyLoggedUser = securityUtils.getCurrentlyLoggedUser();

        if (currentlyLoggedUser == null) {
            throw new AuthorizationServiceException("Unauthorized user");
        }

        return userRepository.findByUsername(currentlyLoggedUser.getUsername()).orElseThrow(UserNotFoundException::new);
    }
}
