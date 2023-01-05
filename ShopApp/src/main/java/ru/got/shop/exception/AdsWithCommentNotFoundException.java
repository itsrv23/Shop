package ru.got.shop.exception;

public class AdsWithCommentNotFoundException extends RuntimeException {

    public AdsWithCommentNotFoundException(Integer adsMessage, Integer commentMessage) {
        super("Ad with id :: " + adsMessage + " doesn't contain AdsComment with id :: " + commentMessage);
    }
}

