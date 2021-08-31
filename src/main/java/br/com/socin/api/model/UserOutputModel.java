package br.com.socin.api.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserOutputModel {

	@NotNull
	private Long id;
	
	@NotBlank
	private String name;
	
	@Email
	private String email;
	
}
