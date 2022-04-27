package co.com.viveres.susy.microserviceuser.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import co.com.viveres.susy.microserviceuser.api.IUserApi;
import co.com.viveres.susy.microserviceuser.dto.UserDto;
import co.com.viveres.susy.microserviceuser.service.IUserService;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/users")
public class UserApiImpl implements IUserApi {

	@Autowired
	private IUserService service;

	@Override
	public ResponseEntity<UserDto> create(UserDto user) {
		UserDto response = this.service.create(user);
		return this.buildCreatResponse(response);
	}
	
	@Override
	public ResponseEntity<UserDto> findById(Long id) {
		UserDto response = this.service.findById(id);
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<UserDto> findByUsername(String username) {
		UserDto response = this.service.findByUsername(username);
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<UserDto> update(Long id, UserDto user) {
		this.service.update(id, user);
		return ResponseEntity.noContent().build();
	}

	private ResponseEntity<UserDto> buildCreatResponse(UserDto response) {
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{user-id}")
				.buildAndExpand(response.getId())
				.toUri();
		return ResponseEntity.created(uri).body(response);
	}
}
