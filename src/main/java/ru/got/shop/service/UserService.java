package ru.got.shop.service;

import ru.got.shop.openapi.dto.NewPassword;
import ru.got.shop.openapi.dto.ResponseWrapperUser;
import ru.got.shop.openapi.dto.User;

public interface UserService {
    User getUserUsingGET (Integer id);
    ResponseWrapperUser getUsersUsingGET();
    NewPassword setPasswordUsingPOST(NewPassword newPassword);
    User updateUserUsingPATCH(User user);
}
