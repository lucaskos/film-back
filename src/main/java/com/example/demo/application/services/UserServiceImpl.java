package com.example.demo.application.services;

import com.example.demo.application.DTO.RegisterDTO;
import com.example.demo.application.DTO.UserDTO;
import com.example.demo.application.DTO.mapper.UserMapper;
import com.example.demo.application.model.user.Role;
import com.example.demo.application.repository.RoleRepo;
import com.example.demo.application.repository.UserRepository;
import com.example.demo.application.model.user.User;
import com.example.demo.application.resource.filter.UserNotFoundException;
import com.example.demo.commons.SecurityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.demo.security.Roles.ADMIN;
import static com.example.demo.security.Roles.USER;

/**
 * Created by Luke on 24.10.2018.
 */
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userDao;
    private BCryptPasswordEncoder bcryptEncoder;
    private UserMapper userMapper;
    private RoleRepo roleRepo;
    private SecurityUtil securityUtil;

    public UserServiceImpl(UserRepository userDao,
                           BCryptPasswordEncoder bcryptEncoder,
                           UserMapper userMapper,
                           RoleRepo roleRepo,
                           SecurityUtil securityUtil) {
        this.userDao = userDao;
        this.bcryptEncoder = bcryptEncoder;
        this.userMapper = userMapper;
        this.roleRepo = roleRepo;
        this.securityUtil = securityUtil;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> findAll() {
        List<UserDTO> list = new ArrayList<>();
        userDao.findAll().stream().forEach(user -> {
            list.add(userMapper.userToUserDto(user));
        });
        return list;
    }

    @PreAuthorize("isAuthenticated()")
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
        return optionalUser.isPresent() ? optionalUser.get() : null;
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
                .map(o -> o.getAuthority()).collect(Collectors.toList()) : null;
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

    public User findByEmail(String email) {
        return userDao.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    public User findByUserName(String username) {
        return userDao.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }
}
