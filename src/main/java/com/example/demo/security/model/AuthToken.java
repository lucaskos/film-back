package com.example.demo.security.model;

import lombok.Data;

import static com.example.demo.config.SecurityConstants.TOKEN_PREFIX;

@Data
public class AuthToken {

	private String token;
	private String username;

	public AuthToken(String token, String username) {
		this.token = TOKEN_PREFIX + token;
		this.username = username;
	}
}