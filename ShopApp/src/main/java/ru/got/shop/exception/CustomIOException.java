package ru.got.shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomIOException extends RuntimeException{
    public CustomIOException() {
        super("IOException, please repeat request again");
    }
}
