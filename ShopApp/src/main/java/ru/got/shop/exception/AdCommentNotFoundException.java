package ru.got.shop.exception;

public class AdCommentNotFoundException extends RuntimeException {

    public AdCommentNotFoundException(Integer message) {
        super("AdComment not found with id :: " + message);
    }
}

