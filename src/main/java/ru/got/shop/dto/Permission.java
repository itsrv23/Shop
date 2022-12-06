package ru.got.shop.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Permission {
    USERS_R("users.read"),
    USERS_RW("users.read:write");
    private final String VALUE;
}
