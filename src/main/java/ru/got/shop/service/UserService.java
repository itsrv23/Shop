package ru.got.shop.service;

import ru.got.shop.dto.NewPasswordDto;
import ru.got.shop.dto.ResponseWrapperUserDto;
import ru.got.shop.dto.UserDto;

public interface UserService {
    UserDto findUser(Integer id);
    UserDto findUser(String login);
    ResponseWrapperUserDto getUsers();
    NewPasswordDto setPassword(NewPasswordDto newPasswordDto, String login);
    UserDto updateUser(UserDto userDto);

}
