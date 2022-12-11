package ru.got.shop.service;

import ru.got.shop.model.dto.NewPassword;
import ru.got.shop.model.dto.ResponseWrapperUser;
import ru.got.shop.model.dto.User;

public interface UserService {
    User getUserUsingGET (Integer id);
    ResponseWrapperUser getUsersUsingGET();
    NewPassword setPasswordUsingPOST(NewPassword newPassword);
    User updateUserUsingPATCH(User user);
}
