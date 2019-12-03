package com.luke.filmdb.application.resource;

import com.luke.filmdb.application.DTO.RegisterDTO;
import com.luke.filmdb.application.DTO.user.UserDTO;
import com.luke.filmdb.application.model.user.User;
import com.luke.filmdb.application.services.UserService;
import com.luke.filmdb.security.jwt.TokenProvider;
import com.luke.filmdb.security.model.AuthToken;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private AuthenticationManager authenticationManager;
    private TokenProvider jwtTokenUtil;

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterDTO user) {
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<java.util.List<UserDTO>> getUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody RegisterDTO user) {
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthToken> authenticateUser(@RequestBody RegisterDTO userDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDTO.getUsername(),
                        userDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = jwtTokenUtil.generateToken(authentication);

        List<String> collect =
                authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        return ResponseEntity.ok(new AuthToken(token, userDTO.getUsername(), collect));
    }

    @GetMapping("/register/checkEmail/{email}")
    public ResponseEntity<Boolean> checkEmailAddress(@PathVariable("email") String email) {
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    @GetMapping("/userRoles")
    public ResponseEntity<java.util.Collection<String>> getLoggedUserRoles() {
        return new ResponseEntity<>(userService.findLoggedUserRoles(), HttpStatus.OK);
    }
}
