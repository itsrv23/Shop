package ru.got.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.got.shop.api.UsersApi;
import ru.got.shop.dto.NewPasswordDto;
import ru.got.shop.dto.ResponseWrapperUserDto;
import ru.got.shop.dto.UserDto;
import ru.got.shop.security.AuthenticationFacade;
import ru.got.shop.security.PermissionService;
import ru.got.shop.service.UserAvatarService;
import ru.got.shop.service.UserService;

import java.util.UUID;

@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
@RestController
public class UserController implements UsersApi, AuthenticationFacade {

    private final UserService userService;
    private final UserAvatarService avatarService;
    private final PermissionService permissionService;

    @Value("${shop.avatar.size}")
    private Long MAX_AVATAR_SIZE;

    @Override
    @PreAuthorize("hasAnyAuthority('users.crud', 'users.full')")
    public ResponseEntity<UserDto> getUserUsingGET(Integer id) {
        permissionService.checkPermissionForUserController(getLogin(), id);
        return ResponseEntity.ok(userService.findUser(id));
    }

    @Override
    @PreAuthorize("hasAuthority('users.crud')")
    public ResponseEntity<UserDto> getUserMeUsingGET() {
        return ResponseEntity.ok(userService.findUser(getLogin()));
    }

    @Override
    @PreAuthorize("hasAuthority('users.full')")
    public ResponseEntity<ResponseWrapperUserDto> getUsersUsingGET() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @Override
    @PreAuthorize("hasAuthority('users.crud')")
    public ResponseEntity<NewPasswordDto> setPasswordUsingPOST(NewPasswordDto newPasswordDto) {
        return ResponseEntity.ok(userService.setPassword(newPasswordDto, getLogin()));
    }

    @Override
    @PreAuthorize("hasAuthority('users.crud')")
    public ResponseEntity<UserDto> updateUserUsingPATCH(UserDto userDto) {
        if (userDto.getEmail() == null) {
            userDto.setEmail(getLogin());
        } // c фронта может прилетать null
        permissionService.checkPermissionForUserController(getLogin(), userDto);
        UserDto user = userService.findUser(getLogin());
        userDto.setEmail(user.getEmail());
        return ResponseEntity.ok(userService.updateUser(userDto));
    }

    @Override
    @PreAuthorize("hasAuthority('users.crud')")
    public ResponseEntity<String> updateUserAvatar(MultipartFile image) {
        if (image.getSize() > MAX_AVATAR_SIZE) {
            return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("The teapot cannot accommodate such a size");
        }
        return ResponseEntity.ok(avatarService.save(image, getLogin()));
    }

    @Override
    public byte[] getImage(UUID uuid) {
        return avatarService.download(uuid);
    }
}
