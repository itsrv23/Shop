package ru.got.shop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import ru.got.shop.openapi.controller.UsersApi;
import ru.got.shop.openapi.dto.NewPassword;
import ru.got.shop.openapi.dto.ResponseWrapperUser;
import ru.got.shop.openapi.dto.User;

@CrossOrigin(value = "http://localhost:3000")
@RestController
public class UserController implements UsersApi {
    @Override
    public ResponseEntity<User> getUserUsingGET(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseWrapperUser> getUsersUsingGET() {
        return null;
    }

    @Override
    public ResponseEntity<NewPassword> setPasswordUsingPOST(NewPassword newPassword) {
        return null;
    }

    @Override
    public ResponseEntity<User> updateUserUsingPATCH(User user) {
        return null;
    }
}
