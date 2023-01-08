package ru.got.shop.exception;

public class AdNotFoundException extends RuntimeException {

    public AdNotFoundException(Integer message) {
        super("Ads not found with id :: " + message);
    }

    public AdNotFoundException(String message) {
        super(message);
    }
}
