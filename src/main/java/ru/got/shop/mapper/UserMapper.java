package ru.got.shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.got.shop.model.User;
import ru.got.shop.dto.UserDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper extends GenerateLink{
    @Mapping(target = "image", expression = "java(generateLink(user))")
    UserDto toDto(User user);

    @Mapping(source = "image", target = "avatarId.uuid")
    User toEntity(UserDto userDto);

    List<UserDto> toDtos(List<User> users);
}
