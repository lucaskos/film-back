package com.luke.filmdb.application.resource;

import com.luke.filmdb.security.model.LoginUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/roles")
public class AuthorizationController {

	@RequestMapping(value = "/getroles", method = RequestMethod.POST)
	public ResponseEntity getUserRoles(@RequestBody LoginUser loginUser) throws AuthenticationException {

		return new ResponseEntity(null, HttpStatus.OK);
	}
}
