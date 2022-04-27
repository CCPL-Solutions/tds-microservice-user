package co.com.viveres.susy.microserviceuser.service;

import co.com.viveres.susy.microserviceuser.dto.UserDto;

public interface IUserService {
	
	UserDto findById(Long id);

	UserDto create(UserDto user);

	UserDto findByUsername(String username);
	
	void update(Long id, UserDto user);

}
