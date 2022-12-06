package ru.got.shop.service;

import ru.got.shop.model.User;
import ru.got.shop.openapi.dto.RegReq;

public interface RegisterService {
    User register(RegReq req);
}
