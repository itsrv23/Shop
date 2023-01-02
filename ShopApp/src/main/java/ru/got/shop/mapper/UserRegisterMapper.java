package ru.got.shop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.got.shop.dto.RegReqDto;
import ru.got.shop.model.User;

@Mapper(componentModel = "spring")
public interface UserRegisterMapper {

    @Mapping(source = "username", target = "email")
    User toEntity(RegReqDto req);
}
