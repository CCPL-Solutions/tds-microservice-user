package co.com.viveres.susy.microserviceuser.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import co.com.viveres.susy.microserviceuser.dto.UserDto;

public interface IUserApi {
	
	@PostMapping(value = "")
	ResponseEntity<UserDto> create(@RequestBody UserDto user);
	
	@GetMapping(value = "/{user-id}")
	ResponseEntity<UserDto> findById(@PathVariable("user-id") Long id);

	@GetMapping(value = "/username/{username}")
	ResponseEntity<UserDto> findByUsername(@PathVariable String username);
	
	@PutMapping(value = "/{user-id}")
	ResponseEntity<UserDto> update(@PathVariable("user-id") Long id, @RequestBody UserDto user);

}
