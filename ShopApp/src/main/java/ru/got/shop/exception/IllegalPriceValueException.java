package ru.got.shop.exception;

public class IllegalPriceValueException extends RuntimeException {
    public IllegalPriceValueException(String message) {
        super(message);
    }
}
