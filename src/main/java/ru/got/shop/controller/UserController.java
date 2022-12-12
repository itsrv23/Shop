package ru.got.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import ru.got.shop.api.UsersApi;
import ru.got.shop.model.dto.NewPasswordDto;
import ru.got.shop.model.dto.ResponseWrapperUserDto;
import ru.got.shop.model.dto.UserDto;
import ru.got.shop.security.AuthenticationFacade;
import ru.got.shop.security.PermissionService;
import ru.got.shop.service.impl.UserServiceImpl;

@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
@RestController
public class UserController implements UsersApi, AuthenticationFacade {

    private final UserServiceImpl userService;
    private final PermissionService permissionService;
    @Override
    public ResponseEntity<UserDto> getUserUsingGET(Integer id) {
        permissionService.checkPermissionForUserController(getLogin(), id);
        return ResponseEntity.ok(userService.findUser(id));
    }

    @Override
    public ResponseEntity<UserDto> getUserMeUsingGET() {
        return ResponseEntity.ok(userService.findUser(getLogin()));
    }

    @Override
    @PreAuthorize("hasAuthority('users.read:write')")
    public ResponseEntity<ResponseWrapperUserDto> getUsersUsingGET() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @Override
    public ResponseEntity<NewPasswordDto> setPasswordUsingPOST(NewPasswordDto newPasswordDto) {
        return ResponseEntity.ok(userService.setPassword(newPasswordDto, getLogin()));
    }

    @Override
    public ResponseEntity<UserDto> updateUserUsingPATCH(UserDto userDto) {
        permissionService.checkPermissionForUserController(getLogin(), userDto);
        UserDto user = userService.findUser(getLogin()); //Берем логин из авторизации
        userDto.setEmail(user.getEmail());
        return ResponseEntity.ok(userService.updateUser(userDto));
    }

}
