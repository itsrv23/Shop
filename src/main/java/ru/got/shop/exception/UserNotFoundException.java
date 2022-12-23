package ru.got.shop.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String login) {
        super("User not found with login :: " + login);
    }

    public UserNotFoundException(Integer message) {
        super("User not found with id :: " + message);
    }
}
