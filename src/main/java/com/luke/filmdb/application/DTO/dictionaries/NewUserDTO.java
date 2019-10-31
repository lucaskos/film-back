package com.luke.filmdb.application.DTO.dictionaries;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class NewUserDTO {
	private Long id;
	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	@NotNull
	private String username;
	@NotNull
	private String password;
	private boolean enabled;
	@NotNull
	private String email;
}
