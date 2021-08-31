package br.com.socin.domain.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.socin.api.model.UserGitModel;
import reactor.core.publisher.Mono;

@Service
public class UserGitService {

	@Autowired
	private WebClient webClient;

	public List<UserGitModel> toList() {

		Mono<UserGitModel[]> listUserGit = this.webClient
				.method(HttpMethod.GET)
				.uri("/users")
				.retrieve()
				.bodyToMono(UserGitModel[].class);

		return Arrays.asList(listUserGit.block());
	}

}
