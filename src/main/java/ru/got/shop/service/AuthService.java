package ru.got.shop.service;

import ru.got.shop.dto.RegReqDto;

public interface AuthService {
    boolean login(String userName, String password);
    boolean register(RegReqDto registerReq);
}
