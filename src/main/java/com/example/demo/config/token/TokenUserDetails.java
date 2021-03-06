package com.example.demo.config.token;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Created by Luke on 24.10.2018.
 */
public class TokenUserDetails extends User {

	private String token;
	private String profileName;

	public TokenUserDetails(String username, String profileName, String password, String token, boolean enabled, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, true, true, true, authorities);
		this.profileName = profileName;
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public String getProfileName() {
		return profileName;
	}
}
