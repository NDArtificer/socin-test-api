package br.com.socin.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.socin.api.model.UserGitModel;
import br.com.socin.domain.service.UserGitService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/users-git")
@AllArgsConstructor
public class UserGitController {

	private UserGitService userGitService;
	
	@GetMapping
	public List<UserGitModel> toList(){
		return userGitService.toList();
	}
	
}
