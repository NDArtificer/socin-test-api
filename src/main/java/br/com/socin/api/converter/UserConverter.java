package br.com.socin.api.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.socin.api.model.UserInputModel;
import br.com.socin.api.model.UserOutputModel;
import br.com.socin.domain.model.User;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserConverter {

	private ModelMapper modelMapper;

	public UserOutputModel toModel(User User) {
		return modelMapper.map(User, UserOutputModel.class);
	}

	public List<UserOutputModel> toCollectionModel(List<User> Users){
		return Users.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
	
	public User toEntity(UserInputModel UserInputModel) {
		return modelMapper.map(UserInputModel, User.class);
	}

	public void copyToDomainObject(UserInputModel UserInput, User User) {
		modelMapper.map(UserInput, User);
	}

}
