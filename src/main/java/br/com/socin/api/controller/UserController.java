package br.com.socin.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.socin.api.converter.UserConverter;
import br.com.socin.api.model.UserInputModel;
import br.com.socin.api.model.UserOutputModel;
import br.com.socin.domain.model.User;
import br.com.socin.domain.repository.UserRepository;
import br.com.socin.domain.service.UserService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

	private UserRepository userRepository;

	private UserService userService;

	private UserConverter userConverter;

	@GetMapping
	public List<UserOutputModel> findAll() {
		return userConverter.toCollectionModel(userRepository.findAll());
	}

	@GetMapping("/{userId}")
	public UserOutputModel find(@PathVariable Long userId) {
		return userConverter.toModel(userService.find(userId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserOutputModel create(@RequestBody @Valid UserInputModel userInput) {
		User userEntity = userConverter.toEntity(userInput);
		return userConverter.toModel(userService.save(userEntity));
	}

	@PutMapping("/{userId}")
	public UserOutputModel update(@PathVariable Long userId, @RequestBody @Valid UserInputModel userInput) {
		User userEntity = userService.find(userId);
		userConverter.copyToDomainObject(userInput, userEntity);
		return userConverter.toModel(userService.save(userEntity));
	}

	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(Long userId) {
		userService.remove(userId);
	}
}
