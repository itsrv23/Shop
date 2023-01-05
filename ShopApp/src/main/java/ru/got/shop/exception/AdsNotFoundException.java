package ru.got.shop.exception;

public class AdsNotFoundException extends RuntimeException {

    public AdsNotFoundException(Integer message) {
        super("Ads not found with id :: " + message);
    }

    public AdsNotFoundException(String message) {
        super(message);
    }
}
