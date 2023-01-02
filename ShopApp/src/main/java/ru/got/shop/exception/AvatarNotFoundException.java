package ru.got.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AvatarNotFoundException extends RuntimeException {

    public AvatarNotFoundException(UUID uuid) {
        super("Avatar not found :: " + uuid.toString());
    }
}
