package co.com.viveres.susy.microserviceuser.service;

import co.com.viveres.susy.microservicecommons.exception.BusinessException;
import co.com.viveres.susy.microservicecommons.exception.NotFoundException;
import co.com.viveres.susy.microservicecommons.util.ResponseMessages;
import co.com.viveres.susy.microserviceuser.dto.RoleDto;
import co.com.viveres.susy.microserviceuser.dto.UserDto;
import co.com.viveres.susy.microserviceuser.entity.RoleEntity;
import co.com.viveres.susy.microserviceuser.entity.UserEntity;
import co.com.viveres.susy.microserviceuser.entity.UserRoleEntity;
import co.com.viveres.susy.microserviceuser.repository.IRoleRepository;
import co.com.viveres.susy.microserviceuser.repository.IUserRepository;
import co.com.viveres.susy.microserviceuser.service.mapper.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository repository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private IMapper mapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDto create(UserDto userDto) {
        this.userAlreadyExists(userDto);

        UserEntity userEntity = this.mapper.mapInUserDtoToUserEntity(userDto, this.passwordEncoder.encode(userDto.getPassword()));
        this.mapInUserRoleDtoListToUserRoleEntityList(userDto.getRoles(), userEntity);

        UserEntity newUserEntity = this.toPersist(userEntity);

        UserDto userDtoOut = this.mapper.mapOutUserEntityToUserDto(newUserEntity);
        this.mapper.mapOutRoleEntityToRoleDto(userDto, newUserEntity);
        return userDtoOut;
    }

    private void userAlreadyExists(UserDto user) {
        if (this.repository.findByUsername(user.getUsername()).isPresent()) {
            throw new BusinessException(ResponseMessages.USER_ALREADY_EXISTS);
        }
    }

    private void mapInUserRoleDtoListToUserRoleEntityList(List<RoleDto> rolesDto, UserEntity userEntity) {
        List<UserRoleEntity> listUserRole = new ArrayList<>();
        rolesDto.forEach(roleDto -> {
            UserRoleEntity userRoleEntity = UserRoleEntity.builder()
                    .user(userEntity)
                    .role(this.findRoleEntityById(roleDto))
                    .build();
            listUserRole.add(userRoleEntity);

        });
        userEntity.setListUserRole(listUserRole);
    }

    private RoleEntity findRoleEntityById(RoleDto roleDto) {
        return this.roleRepository.findById(roleDto.getId())
                .orElseThrow(() -> new NotFoundException(ResponseMessages.ROLE_DOES_NOT_EXIST));
    }

    private UserEntity toPersist(UserEntity userEntiy) {
        return this.repository.save(userEntiy);
    }

    @Override
    public UserDto findById(Long id) {
        UserEntity userEntity = this.findUserById(id);
        UserDto userDtoOut = this.mapper.mapOutUserEntityToUserDto(userEntity);
        this.mapper.mapOutRoleEntityToRoleDto(userDtoOut, userEntity);
        return userDtoOut;
    }

    @Override
    public UserDto findByUsername(String username) {
        UserEntity userEntity = this.repository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ResponseMessages.USER_DOES_NOT_EXIST));

        UserDto userDtoOut = this.mapper.mapOutUserEntityToUserDto(userEntity);
        this.mapper.mapOutRoleEntityToRoleDto(userDtoOut, userEntity);
        return userDtoOut;
    }

    @Override
    public void update(Long id, UserDto userDto) {
        UserEntity userEntity = this.findUserById(id);
        userEntity = this.mapper.mapInUserDtoToUserEntityUpdate(userDto, userEntity);
        this.repository.save(userEntity);
    }

    private UserEntity findUserById(Long userId) {
        return this.repository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ResponseMessages.PRODUCT_DOES_NOT_EXIST));
    }

}
