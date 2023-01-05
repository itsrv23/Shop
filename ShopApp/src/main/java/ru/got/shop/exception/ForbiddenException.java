package ru.got.shop.exception;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException() {
        super("You Shall Not Pass (c) Gandalf");
    }

    public ForbiddenException(String message) {
        super(message);
    }
}
