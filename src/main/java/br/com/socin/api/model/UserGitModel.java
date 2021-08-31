package br.com.socin.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserGitModel {

	private Long id;
	private String login;
	private String type;
	private String node_id;
	private String site_admin;
}
