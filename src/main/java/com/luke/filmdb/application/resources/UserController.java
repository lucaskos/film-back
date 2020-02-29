package com.luke.filmdb.application.resources;

import com.luke.filmdb.application.DTO.RegisterDTO;
import com.luke.filmdb.application.DTO.user.UserDTO;
import com.luke.filmdb.application.model.user.User;
import com.luke.filmdb.application.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterDTO user) {
        User user1 = userService.saveNewUser(user);
        return new ResponseEntity<>(user1, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<java.util.List<UserDTO>> getUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO user) {
        return new ResponseEntity<>(userService.update(user), HttpStatus.OK);
    }

//    @PostMapping("/login")
//    public ResponseEntity<AuthToken> authenticateUser(@RequestBody RegisterDTO userDTO) {
//
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        userDTO.getUsername(),
//                        userDTO.getPassword()
//                )
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        final String token = jwtTokenUtil.generateToken(authentication);
//
//        List<String> collect =
//                authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
//
//        return ResponseEntity.ok(new AuthToken(token, userDTO.getUsername(), collect));
//    }

    @GetMapping("/register/checkEmail/{email}")
    public ResponseEntity<Boolean> checkEmailAddress(@PathVariable("email") String email) {
        //todo
        return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
    }

    @GetMapping("/userRoles")
    public ResponseEntity<java.util.Collection<String>> getLoggedUserRoles() {
        return new ResponseEntity<>(userService.findLoggedUserRoles(), HttpStatus.OK);
    }
}
