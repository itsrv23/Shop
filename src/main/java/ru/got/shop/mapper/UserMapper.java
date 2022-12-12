package ru.got.shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.got.shop.model.User;
import ru.got.shop.model.dto.UserDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "phone", target = "phone")
    User toEntity(UserDto userDto);

    List<UserDto> toDtos(List<User> users);
}
