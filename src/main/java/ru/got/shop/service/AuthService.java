package ru.got.shop.service;

import ru.got.shop.model.dto.RegReqDto;

public interface AuthService {
    boolean login(String userName, String password);
    boolean register(RegReqDto registerReq, RegReqDto.RoleEnum role);
}
