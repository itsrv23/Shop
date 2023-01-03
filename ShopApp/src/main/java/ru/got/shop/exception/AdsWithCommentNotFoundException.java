package ru.got.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AdsWithCommentNotFoundException extends RuntimeException {

    public AdsWithCommentNotFoundException(Integer adsMessage, Integer commentMessage) {
        super("Ad with id :: " + adsMessage + " doesn't contain AdsComment with id :: " + commentMessage);
    }
}

