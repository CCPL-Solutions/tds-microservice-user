package co.com.viveres.susy.microserviceuser.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import co.com.viveres.susy.microservicecommons.entity.MessageEntity;
import co.com.viveres.susy.microservicecommons.exceptions.GenericException;
import co.com.viveres.susy.microservicecommons.repository.IMessageRepository;
import co.com.viveres.susy.microserviceuser.dto.RoleDto;
import co.com.viveres.susy.microserviceuser.dto.UserDto;
import co.com.viveres.susy.microserviceuser.entity.UserEntity;
import co.com.viveres.susy.microserviceuser.entity.UserRoleEntity;
import co.com.viveres.susy.microserviceuser.repository.IRoleRepository;
import co.com.viveres.susy.microserviceuser.repository.IUserRepository;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserRepository repository;
	
	@Autowired
	private IRoleRepository roleRepository;

	@Autowired
	private IMessageRepository messageRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public UserDto create(UserDto user) {
		this.userAlreadyExists(user);
		UserEntity userEntiy = this.mapInUserDtoToUserEntity(user);
		UserEntity newUserEntiy = this.toPersist(userEntiy);
		return this.mapOutUserEntityToUserDto(newUserEntiy);
	}

	private void userAlreadyExists(UserDto user) {
		if (this.repository.findByUsername(user.getUsername()).isPresent()) {
			throw this.setGenericException("viveres-susy.usuarios.usuario-ya-existe", new String());
		}
	}

	private UserEntity mapInUserDtoToUserEntity(UserDto userDto) {
		UserEntity userEntity = new UserEntity();
		userEntity.setUsername(userDto.getUsername());
		userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
		userEntity.setEnabled(Boolean.TRUE);
		userEntity.setName(userDto.getName());
		userEntity.setLastName(userDto.getLastName());
		userEntity.setEmail(userDto.getEmail());
		
		this.mapInUserRoleDtoListToUserRoleEntityList(userDto.getRoles(), userEntity);
		
		return userEntity;
	}
	
	private void mapInUserRoleDtoListToUserRoleEntityList(List<RoleDto> rolesDto, UserEntity userEntity){
		List<UserRoleEntity> listUserRole = new ArrayList<>();
		rolesDto.forEach(roleDto -> {
			
			UserRoleEntity userRoleEntity = new UserRoleEntity();
			userRoleEntity.setUser(userEntity);
			userRoleEntity.setRole(this.roleRepository.findById(roleDto.getId()).orElseThrow());
			listUserRole.add(userRoleEntity);
			
		});
		userEntity.setListUserRole(listUserRole);
	}

	private UserEntity toPersist(UserEntity userEntiy) {
		return this.repository.save(userEntiy);
	}

	private UserDto mapOutUserEntityToUserDto(UserEntity userEntiy) {
		UserDto userDto = new UserDto();
		userDto.setId(userEntiy.getId());
		userDto.setUsername(userEntiy.getUsername());
		userDto.setPassword(userEntiy.getPassword());
		userDto.setEnabled(userEntiy.getEnabled());
		userDto.setName(userEntiy.getName());
		userDto.setLastName(userEntiy.getLastName());
		userDto.setEmail(userEntiy.getEmail());
		userDto.setAttempts(userEntiy.getAttempts());
		
		List<RoleDto> roles = new ArrayList<>();
		userEntiy.getListUserRole().forEach(roleEntity -> {
			RoleDto role = new RoleDto();
			role.setId(roleEntity.getRole().getId());
			role.setName(roleEntity.getRole().getName());
			roles.add(role);
		});
		
		userDto.setRoles(roles);
		return userDto;
	}
	
	@Override
	public UserDto findById(Long id) {
		UserEntity userEntiy = this.repository.findById(id)
				.orElseThrow(() -> this.setGenericException("viveres-susy.usuarios.usuario-no-existe", String.valueOf(id)));
		return this.mapOutUserEntityToUserDto(userEntiy);
	}

	@Override
	public UserDto findByUsername(String username) {
		UserEntity userEntiy = this.repository.findByUsername(username)
				.orElseThrow(() -> this.setGenericException("viveres-susy.usuarios.usuario-no-existe", username));

		return this.mapOutUserEntityToUserDto(userEntiy);
	}
	
	@Override
	public void update(Long id, UserDto userDto) {
		UserEntity userEntity = this.repository.findById(id)
				.orElseThrow(() -> this.setGenericException("viveres-susy.usuarios.usuario-no-existe", String.valueOf(id)));
		
		userEntity.setUsername(userDto.getUsername());
		userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
		userEntity.setEnabled(userDto.getEnabled());
		userEntity.setName(userDto.getName());
		userEntity.setLastName(userDto.getLastName());
		userEntity.setEmail(userDto.getEmail());
		
		this.mapInUserRoleDtoListToUserRoleEntityList(userDto.getRoles(), userEntity);
		
		this.repository.save(userEntity);
	}

	private GenericException setGenericException(String responseMessage, String value) {

		MessageEntity message = this.messageRepository.findById(responseMessage)
				.orElseThrow(NoSuchElementException::new);
		message.setDescripction(String.format(message.getDescripction(), value));

		return new GenericException(message);
	}

}
