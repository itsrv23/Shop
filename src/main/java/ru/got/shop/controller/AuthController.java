package ru.got.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import ru.got.shop.model.User;
import ru.got.shop.openapi.controller.AuthApi;
import ru.got.shop.openapi.dto.RegReq;
import ru.got.shop.service.AuthService;
import ru.got.shop.service.RegisterService;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final AuthService authService;
    private final RegisterService registerService;

    @Override
    public ResponseEntity<Object> loginUsingPOST(ru.got.shop.openapi.dto.LoginReq req) {
        if (authService.login(req.getUsername(), req.getPassword())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Override
    public ResponseEntity<Object> registerUsingPOST(RegReq req) {
        //TODO  не передаются все данные о пользователе при регистрации. Уточнить
        User register = registerService.register(req);
        if (register != null) {
            return ResponseEntity.ok(register);
        } else {
            //todo нужна еще проверка и обработка на фронте если пользователь уже есть в базе с таким логином
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
