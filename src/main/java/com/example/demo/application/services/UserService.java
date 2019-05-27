package com.example.demo.application.services;

import com.example.demo.application.DTO.UserDTO;
import com.example.demo.application.DTO.dictionaries.NewUserDTO;
import com.example.demo.application.DTO.mapper.UserMapper;
import com.example.demo.application.model.user.Role;
import com.example.demo.application.model.user.User;
import com.example.demo.application.repository.RoleRepo;
import com.example.demo.application.repository.UserRepository;
import com.example.demo.config.service.TokenService;
import com.example.demo.config.service.UsernameTakenException;
import com.example.demo.security.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Luke on 24.10.2018.
 */
@Service("customservice")
public class UserService {
    public static final String USER_ROLE = "user";
    private UserRepository repository;
    private PasswordEncoder passwordEncoder;
    private TokenService tokenService;
    private UserMapper userMapper;
    private RoleRepo roleRepo;

    @Autowired
    public UserService(UserMapper userMapper, UserRepository repository, PasswordEncoder passwordEncoder, TokenService tokenService, RoleRepo roleRepo) {
        this.userMapper = userMapper;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.roleRepo = roleRepo;
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    public User findByUserName(String username) {
        return repository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

//    @Transactional
//    public UserDTO saveUser(String email, String username, String password) {
//        if (repository.findByEmail(email).isPresent()) {
//            throw new UsernameTakenException("Username is already taken");
//        }
//        Role roleName = new Role(email, USER_ROLE);
//        User user = repository.saveAndFlush(new User(email, username, passwordEncoder.encode(password), true, Collections.singletonList(roleName)));
//        return userMapper.userDtoToUser(user);
//        //return tokenService.encode(user);
//
//    }

    @Transactional
    public UserDTO save(NewUserDTO userDTO) {
        if (repository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new UsernameTakenException("Username is already taken");
        }
        Role role = getDefaultRole();
        String password = passwordEncoder.encode(userDTO.getPassword());
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setEnabled(true);
        user.setPassword(password);
        user.setRoles(Collections.singletonList(role));
        User save = repository.save(user);
//        User user = repository.saveUser(new User(userDTO.getEmail(), userDTO.getUsername(), encode, true, Collections.singletonList(roleName)));
        return userMapper.userToUserDto(save);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserDTO> getAllUsers() {
        List<User> all = repository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        all.stream().forEach(e->userDTOList.add(userMapper.userToUserDto(e)));
        return userDTOList;
    }

    Role getDefaultRole() {
        Role role_user = roleRepo.findRoleByRoleName("ROLE_USER");
        return role_user;
    }

    User findById(Long id) {
        return repository.findById(id).get();
    }

//    @PostConstruct
//    public void register() {
//        registration.newUserRequest().subscribe(message -> {
//            NewUser payload = (NewUser) message.getPayload();
//            saveUser(payload.getEmail(), payload.getUsername(), payload.getPassword());
//        });
//    }
}
//https://g00glen00b.be/spring-boot-jwt/