package com.example.demo.config;

import com.example.demo.Role;
import com.example.demo.User;
import com.example.demo.UserRepository;
import com.example.demo.config.model.NewUser;
import com.example.demo.config.service.TokenService;
import com.example.demo.config.service.UsernameTakenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
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

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public String save(String email, String username, String password) {
        if (repository.findByEmail(email).isPresent()) {
            throw new UsernameTakenException("Username is already taken");
        }
        Role role = new Role(email, USER_ROLE);
        User user = repository.saveAndFlush(new User(email, username, passwordEncoder.encode(password), true, Collections.singletonList(role)));
        return tokenService.encode(user);
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