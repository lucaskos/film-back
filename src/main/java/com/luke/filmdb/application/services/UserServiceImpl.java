package com.luke.filmdb.application.services;

import com.luke.filmdb.application.DTO.RegisterDTO;
import com.luke.filmdb.application.DTO.user.UserDTO;
import com.luke.filmdb.application.DTO.mapper.UserMapper;
import com.luke.filmdb.application.model.user.Role;
import com.luke.filmdb.application.repository.RoleRepo;
import com.luke.filmdb.application.repository.UserRepository;
import com.luke.filmdb.application.model.user.User;
import com.luke.filmdb.application.resource.filter.UserNotFoundException;
import com.luke.filmdb.commons.SecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.luke.filmdb.security.Roles.ADMIN;
import static com.luke.filmdb.security.Roles.USER;

/**
 * Created by Luke on 24.10.2018.
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userDao;
    private final BCryptPasswordEncoder bcryptEncoder;
    private final UserMapper userMapper;
    private final RoleRepo roleRepo;
    private final SecurityUtil securityUtil;

    @Secured(ADMIN)
    public List<UserDTO> findAll() {
        List<UserDTO> list = new ArrayList<>();
        userDao.findAll().stream().forEach(user -> {
            list.add(userMapper.userToUserDto(user));
        });
        return list;
    }

    @PreAuthorize("isAuthenticated()")
    @Secured(ADMIN)
    public void delete(Long id) {
        User userToRemove = findById(id);

        if (isActualUserLoggedOrAdmin(userToRemove)) {
            userDao.delete(userToRemove);
        } else {
            throw new AuthenticationServiceException("You're cannot perform this operation");
        }
    }

    private boolean isActualUserLoggedOrAdmin(User loggedUser) {
        org.springframework.security.core.userdetails.User user = this.securityUtil.getCurrentlyLoggedUser();
        boolean hasAdminAuthority = this.securityUtil.hasUserAuthority(ADMIN);

        return user.getUsername().equals(loggedUser.getUsername()) || hasAdminAuthority;
    }

    public User findOne(String username) {
        return userDao.findByUsername(username).get();
    }

    public User findById(Long id) {
        Optional<User> optionalUser = userDao.findById(id);
        return optionalUser.orElse(null);
    }

    public UserDTO update(UserDTO UserDTO) {
        User user = findById(UserDTO.getId());
        if (user != null) {
            BeanUtils.copyProperties(UserDTO, user, "password");
            userDao.save(user);
        }
        return UserDTO;
    }

    public Collection<String> findLoggedUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList()) : null;
    }


    //todo test
    public User saveUser(RegisterDTO user) {
        Role defaultRole = getDefaultRole();

        User userEntity = userMapper.userDtoToUser(user);

        if (isActualUserLoggedOrAdmin(userEntity)) {
            userEntity.setRoles(Collections.singletonList(defaultRole));
            userDao.save(userEntity);
            //todo update user
        } else {
            //todo save new userEntity
            userEntity.setPassword(bcryptEncoder.encode(userEntity.getPassword()));
            userEntity.setRoles(Collections.singletonList(roleRepo.findRoleByRoleName(USER)));
        }
        return userDao.save(userEntity);
    }


    Role getDefaultRole() {
        Role role_user = roleRepo.findRoleByRoleName(USER);
        return role_user;
    }

    public User findByEmail(String email) throws UserNotFoundException {
        return userDao.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    public User findByUserName(String username) throws UserNotFoundException {
        return userDao.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }
}
