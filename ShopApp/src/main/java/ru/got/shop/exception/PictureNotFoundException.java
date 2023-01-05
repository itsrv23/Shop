package ru.got.shop.exception;

import java.util.UUID;

public class PictureNotFoundException extends RuntimeException {
    public PictureNotFoundException(UUID message) {
        super("Picture doesn't exist wit UUID :: ".concat(message.toString()));
    }
}
