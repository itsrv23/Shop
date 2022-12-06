package ru.got.shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.got.shop.model.User;
import ru.got.shop.openapi.dto.RegReq;

@Mapper(componentModel = "spring")
public interface UserRegisterMapper {

    @Mapping(source = "username", target = "email")
    @Mapping(source = "password", target = "password")
    User toEntity(RegReq req);
}
