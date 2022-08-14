package co.com.viveres.susy.microserviceuser.service.mapper;

import co.com.viveres.susy.microserviceuser.dto.UserDto;
import co.com.viveres.susy.microserviceuser.entity.UserEntity;

public interface IMapper {

    UserEntity mapInUserDtoToUserEntity(UserDto userDto, String password);

    UserDto mapOutUserEntityToUserDto(UserEntity userEntity);

    void mapOutRoleEntityToRoleDto(UserDto userDto, UserEntity userEntity);

    UserEntity mapInUserDtoToUserEntityUpdate(UserDto userDto, UserEntity userEntity);

}
