package co.com.viveres.susy.microserviceuser.service.mapper.impl;

import co.com.viveres.susy.microserviceuser.dto.RoleDto;
import co.com.viveres.susy.microserviceuser.dto.UserDto;
import co.com.viveres.susy.microserviceuser.entity.UserEntity;
import co.com.viveres.susy.microserviceuser.service.mapper.IMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MapperImpl implements IMapper {
    @Override
    public UserEntity mapInUserDtoToUserEntity(UserDto userDto, String password) {
        return UserEntity.builder()
                .username(userDto.getUsername())
                .password(password)
                .enabled(Boolean.TRUE)
                .name(userDto.getName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .build();
    }

    @Override
    public UserDto mapOutUserEntityToUserDto(UserEntity userEntity) {
        return UserDto.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .enabled(userEntity.getEnabled())
                .name(userEntity.getName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .attempts(userEntity.getAttempts())
                .build();
    }

    @Override
    public void mapOutRoleEntityToRoleDto(UserDto userDto, UserEntity userEntity) {
        List<RoleDto> roles = new ArrayList<>();
        userEntity.getListUserRole().forEach(roleEntity -> {
            RoleDto role = RoleDto.builder()
                    .id(roleEntity.getRole().getId())
                    .name(roleEntity.getRole().getName())
                    .build();
            roles.add(role);
        });
        userDto.setRoles(roles);
    }

    @Override
    public UserEntity mapInUserDtoToUserEntityUpdate(UserDto userDto, UserEntity userEntity) {
        return UserEntity.builder()
                .id(userEntity.getId())
                .username(userDto.getUsername())
                .enabled(userDto.getEnabled())
                .name(userDto.getName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .attempts(userDto.getAttempts())
                .build();
    }
}
