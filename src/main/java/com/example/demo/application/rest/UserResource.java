package com.example.demo.application.rest;

import com.example.demo.application.dto.UserDTO;
import com.example.demo.repository.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/filmdb/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserResource {

    private UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public UserDTO register(@RequestBody UserDTO user) {
        UserDTO save = userService.save(user);
        return save;
    }
}
