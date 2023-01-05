package ru.got.shop.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.got.shop.exception.*;

import javax.management.InstanceNotFoundException;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({
            AdsCommentNotFoundException.class,
            AdsNotFoundException.class,
            AdsWithCommentNotFoundException.class,
            AvatarNotFoundException.class,
            UserNotFoundException.class,
            PictureNotFoundException.class
    })
    @ResponseStatus(NOT_FOUND)
    @ResponseBody
    public ApiError handleNotFoundExceptions(RuntimeException exception, HttpServletRequest request) {
        return getApiError(exception, NOT_FOUND, request);
    }

    @ExceptionHandler({
            CustomIOException.class,
            RegisterException.class,
            MethodArgumentNotValidException.class
    })
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public ApiError handleCustomIoException(Exception exception, HttpServletRequest request) {
        return getApiError(exception, BAD_REQUEST, request);
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(FORBIDDEN)
    @ResponseBody
    public ApiError handleForbiddenException(ForbiddenException exception, HttpServletRequest request) {
        return getApiError(exception, NOT_FOUND, request);
    }

    @ExceptionHandler({
            AccessDeniedException.class,
            InstanceNotFoundException.class
    })
    @ResponseStatus(UNAUTHORIZED)
    @ResponseBody
    public ApiError handleNoAccessException(Exception exception, HttpServletRequest request) {
        return getApiError(exception, UNAUTHORIZED, request);
    }

    private ApiError getApiError(Exception exception, HttpStatus status, HttpServletRequest request) {
        return ApiError.builder()
                .message(exception.getMessage())
                .method(request.getMethod())
                .path(request.getRequestURL().toString())
                .status(status.value())
                .reason(status.getReasonPhrase())
                .build();
    }
}
