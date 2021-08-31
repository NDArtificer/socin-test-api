package br.com.socin.api.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInputModel {

	@NotBlank
	private String name;

	@NotBlank
	private String password;
	
	@Email
	@NotBlank
	private String email;
	
}
