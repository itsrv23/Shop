package ru.got.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.got.shop.api.UsersApi;
import ru.got.shop.model.dto.NewPasswordDto;
import ru.got.shop.model.dto.ResponseWrapperUserDto;
import ru.got.shop.model.dto.UserDto;
import ru.got.shop.security.AuthenticationFacade;
import ru.got.shop.security.PermissionService;
import ru.got.shop.service.UserAvatarService;
import ru.got.shop.service.UserService;
import ru.got.shop.service.impl.UserServiceImpl;

import java.util.UUID;

@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
@RestController
public class UserController implements UsersApi, AuthenticationFacade {

    private final UserService userService;
    private final UserAvatarService avatarService;
    private final PermissionService permissionService;

    private final Long MAX_AVATAR_SIZE = (long) (2 * 1024 * 1024);

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
        if (userDto.getEmail() == null){userDto.setEmail(getLogin());} // c фронта может прилетать null
        permissionService.checkPermissionForUserController(getLogin(), userDto);
        UserDto user = userService.findUser(getLogin()); //Берем логин из авторизации
        userDto.setEmail(user.getEmail());
        return ResponseEntity.ok(userService.updateUser(userDto));
    }

    @Override
    public ResponseEntity<String> updateUserAvatar(MultipartFile image) {
        if (image.getSize() > MAX_AVATAR_SIZE) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("Чайник не может вместить в себя такой размер");
        }
        return ResponseEntity.ok(avatarService.save(image, getLogin()));
    }

    @Override
    public byte[] getImage(UUID uuid) {
        return avatarService.download(uuid);
    }
}
