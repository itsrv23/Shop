package ru.got.shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.got.shop.openapi.dto.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toDto(ru.got.shop.model.User user);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "phone", target = "phone")
    ru.got.shop.model.User toEntity(User user);
}
