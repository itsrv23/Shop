package ru.got.shop.exception;

public class AdNotFoundException extends RuntimeException {

    public AdNotFoundException(Integer message) {
        super("Ad not found with id :: " + message);
    }

    public AdNotFoundException(String message) {
        super(message);
    }
}
