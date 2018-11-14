package com.example.demo.repository;

import com.example.demo.application.dto.UserDTO;
import com.example.demo.application.dto.mapper.UserMapper;
import com.example.demo.config.UserNotFoundException;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.config.service.TokenService;
import com.example.demo.config.service.UsernameTakenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

/**
 * Created by Luke on 24.10.2018.
 */
@Service
public class UserService {
    public static final String USER_ROLE = "user";
    private UserRepository repository;
    private PasswordEncoder passwordEncoder;
    private TokenService tokenService;
    private UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper, UserRepository repository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userMapper = userMapper;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

//    @Transactional
//    public UserDTO save(String email, String username, String password) {
//        if (repository.findByEmail(email).isPresent()) {
//            throw new UsernameTakenException("Username is already taken");
//        }
//        Role role = new Role(email, USER_ROLE);
//        User user = repository.saveAndFlush(new User(email, username, passwordEncoder.encode(password), true, Collections.singletonList(role)));
//        return userMapper.userDtoToUser(user);
//        //return tokenService.encode(user);
//
//    }

    @Transactional
    public UserDTO save(UserDTO userDTO) {
        if (repository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new UsernameTakenException("Username is already taken");
        }
        Role role = new Role(userDTO.getEmail(), USER_ROLE);
        User user = repository.saveAndFlush(new User(userDTO.getEmail(), userDTO.getUsername(), passwordEncoder.encode(userDTO.getPassword()), true, Collections.singletonList(role)));
        return userMapper.userDtoToUser(user);
        //return tokenService.encode(user);

    }


//    @PostConstruct
//    public void register() {
//        registration.newUserRequest().subscribe(message -> {
//            NewUser payload = (NewUser) message.getPayload();
//            save(payload.getEmail(), payload.getUsername(), payload.getPassword());
//        });
//    }
}
//https://g00glen00b.be/spring-boot-jwt/