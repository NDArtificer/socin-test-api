package br.com.socin.domain.service;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.socin.domain.exception.BusinessException;
import br.com.socin.domain.exception.EntityNotFoundException;
import br.com.socin.domain.model.User;
import br.com.socin.domain.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

	private UserRepository userRepository;

	public User find(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Entity not found!"));
	}

	@Transactional
	public User save(@Valid User user) {
		boolean emailExists = userRepository.findByEmail(user.getEmail()).stream()
				.anyMatch(clientExists -> !clientExists.equals(user));
		if (emailExists) {
			throw new BusinessException("There is already a client with the email entered, inform another one!");
		}
		return userRepository.save(user);

	}

	@Transactional
	public void remove(Long userId) {
		try {
			userRepository.deleteById(userId);
		} catch (Exception e) {
			throw new BusinessException("Failed to remove entity");
		}

	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found!"));

		return org.springframework.security.core.userdetails.User.builder().username(user.getEmail())
				.password(user.getPassword()).roles("USER").build();
	}

}
