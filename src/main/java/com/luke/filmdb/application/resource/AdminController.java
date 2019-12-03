package com.luke.filmdb.application.resource;

import com.luke.filmdb.application.DTO.user.UserDTO;
import com.luke.filmdb.application.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    @GetMapping(value = "/list")
    public ResponseEntity getAllUsers() {
        List<UserDTO> allUsers = userService.findAll();
        return new ResponseEntity(allUsers, HttpStatus.OK);
    }

}
