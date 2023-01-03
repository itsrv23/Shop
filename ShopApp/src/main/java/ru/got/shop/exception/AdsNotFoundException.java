package ru.got.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AdsNotFoundException extends RuntimeException {

    public AdsNotFoundException(Integer message) {
        super("Ads not found with id :: " + message);
    }
}
