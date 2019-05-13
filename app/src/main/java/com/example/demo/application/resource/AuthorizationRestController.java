package com.example.demo.application.resource;

import com.example.demo.config.model.TokenUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Luke on 24.10.2018.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class AuthorizationRestController {
    @GetMapping("/token")
    public String getToken(@AuthenticationPrincipal TokenUserDetails principal) {
        String test = "new string";
        return principal.getToken();
    }
}
