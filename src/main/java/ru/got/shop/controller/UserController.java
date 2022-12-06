package ru.got.shop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import ru.got.shop.openapi.controller.UsersApi;
import ru.got.shop.openapi.dto.NewPassword;
import ru.got.shop.openapi.dto.ResponseWrapperUser;
import ru.got.shop.openapi.dto.User;
import ru.got.shop.service.impl.UserServiceImpl;

@CrossOrigin(value = "http://localhost:3000")
@RestController
public class UserController implements UsersApi {

    private final UserServiceImpl userService;
    public UserController (UserServiceImpl userService) {
        this.userService = userService;
    }
    @Override
    public ResponseEntity<User> getUserUsingGET(Integer id) {
        return ResponseEntity.ok(userService.getUserUsingGET(id));
    }

    @Override
    public ResponseEntity<ResponseWrapperUser> getUsersUsingGET() {
        return ResponseEntity.ok(userService.getUsersUsingGET());
    }

    @Override
    public ResponseEntity<NewPassword> setPasswordUsingPOST(NewPassword newPassword) {
        return ResponseEntity.ok(userService.setPasswordUsingPOST(newPassword));
    }

    @Override
    public ResponseEntity<User> updateUserUsingPATCH(User user) {
        return ResponseEntity.ok(userService.updateUserUsingPATCH(user));
    }
}
