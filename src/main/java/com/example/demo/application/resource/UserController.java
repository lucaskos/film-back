package com.example.demo.application.resource;

import com.example.demo.application.DTO.UserDTO;
import com.example.demo.application.model.user.User;
import com.example.demo.security.UserService;
import com.example.demo.security.jwt.TokenProvider;
import com.example.demo.security.model.AuthToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class UserController {

    private UserService userService;
    private AuthenticationManager authenticationManager;
    private TokenProvider jwtTokenUtil;

    public UserController(UserService userService, AuthenticationManager authenticationManager, TokenProvider jwtTokenUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody UserDTO user) {
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity getUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity updateUser(@RequestBody UserDTO user) {
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity authenticateUser(@RequestBody UserDTO userDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDTO.getUsername(),
                        userDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return ResponseEntity.ok(new AuthToken("Bearer " + token, userDTO.getUsername()));
    }

    @GetMapping("/register/checkEmail/{email}")
    public ResponseEntity checkEmailAddress(@PathVariable("email") String email) {
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    @GetMapping("/userRoles")
    public ResponseEntity getLoggedUserRoles() {
        return new ResponseEntity(userService.findLoggedUserRoles(), HttpStatus.OK);
    }
}
