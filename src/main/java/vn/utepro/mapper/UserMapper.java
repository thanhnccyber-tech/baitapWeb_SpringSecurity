package vn.utepro.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.utepro.dto.UserDTO;
import vn.utepro.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roleName", source = "role.name")
    UserDTO toDTO(User user);
}