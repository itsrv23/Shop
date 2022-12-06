package ru.got.shop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import ru.got.shop.openapi.controller.UsersApi;
import ru.got.shop.openapi.dto.NewPassword;
import ru.got.shop.openapi.dto.ResponseWrapperUser;
import ru.got.shop.openapi.dto.User;

import java.util.List;

@CrossOrigin(value = "http://localhost:3000")
@RestController
public class UserController implements UsersApi {
    @Override
    public ResponseEntity<User> getUserUsingGET(Integer id) {
        return ResponseEntity.ok(new User("string@mail.ru", "Ivan", 1, "Danko", "+19982334554"));
    }

    @Override
    public ResponseEntity<ResponseWrapperUser> getUsersUsingGET() {
        ResponseWrapperUser user = new ResponseWrapperUser();
        user.setCount(0);
        user.setResults(List.of(new User()));
        return ResponseEntity.ok(user);
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
