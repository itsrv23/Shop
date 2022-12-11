package ru.got.shop.service;

import ru.got.shop.model.dto.RegReq;

public interface AuthService {
    boolean login(String userName, String password);
    boolean register(RegReq registerReq, RegReq.RoleEnum role);
}
