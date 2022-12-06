package ru.got.shop.service.impl;

import org.springframework.stereotype.Service;
import ru.got.shop.openapi.dto.NewPassword;
import ru.got.shop.openapi.dto.ResponseWrapperUser;
import ru.got.shop.openapi.dto.User;
import ru.got.shop.service.UserService;
import ru.got.shop.constant.UserFactory;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public User getUserUsingGET(Integer id) {
        return UserFactory.getUser();
    }

    @Override
    public ResponseWrapperUser getUsersUsingGET() {
        return UserFactory.getResponseWrapperUser();
    }

    @Override
    public NewPassword setPasswordUsingPOST(NewPassword newPassword) {
        return UserFactory.getNewPassword();
    }

    @Override
    public User updateUserUsingPATCH(User user) {
        return UserFactory.getUser();
    }
}
