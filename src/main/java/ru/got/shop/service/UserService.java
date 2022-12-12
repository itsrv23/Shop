package ru.got.shop.service;

import ru.got.shop.model.dto.NewPasswordDto;
import ru.got.shop.model.dto.ResponseWrapperUserDto;
import ru.got.shop.model.dto.UserDto;

public interface UserService {
    UserDto findUser(Integer id);
    UserDto findUser(String login);
    ResponseWrapperUserDto getUsers();
    NewPasswordDto setPassword(NewPasswordDto newPasswordDto, String login);
    UserDto updateUser(UserDto userDto);

}
