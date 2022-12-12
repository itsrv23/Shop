package ru.got.shop.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import ru.got.shop.controller.AuthApi;
import ru.got.shop.model.dto.LoginReqDto;
import ru.got.shop.model.dto.RegReqDto;
import ru.got.shop.security.AuthenticationFacade;
import ru.got.shop.service.AuthService;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi, AuthenticationFacade {

    private final AuthService authService;

    @Override
    public ResponseEntity<Authentication> loginUsingPOST(LoginReqDto req) {
        if (authService.login(req.getUsername(), req.getPassword())) {
            return ResponseEntity.ok(getAuthentication());
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Override
    public ResponseEntity<Object> registerUsingPOST(RegReqDto req) {
        //TODO  не передаются все данные о пользователе при регистрации. Уточнить
        boolean register = authService.register(req, RegReqDto.RoleEnum.USER);
        if (register) {
            return ResponseEntity.ok().build();
        } else {
            //todo нужна еще проверка и обработка на фронте если пользователь уже есть в базе с таким логином
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
