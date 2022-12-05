package ru.got.shop.service;

import ru.got.shop.dto.RegisterReq;
import ru.got.shop.dto.Role;

public interface AuthService {
    boolean login(String userName, String password);
    boolean register(RegisterReq registerReq, Role role);
}
