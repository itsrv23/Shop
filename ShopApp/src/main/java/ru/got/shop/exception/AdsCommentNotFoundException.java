package ru.got.shop.exception;

public class AdsCommentNotFoundException extends RuntimeException {

    public AdsCommentNotFoundException(Integer message) {
        super("AdsComment not found with id :: " + message);
    }
}

