package ru.got.shop.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ExceptionHandler {
    // Делал Серега)) с прошлого проекта

    /**
     * Обработка EntityNotFoundException исключений и проброс текста
     * ошибки в контроллер
     *
     * @param e Exception
     * @return ResponseEntity
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException e) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode JSONObject = mapper.createObjectNode();
        JSONObject.put(e.getMessage(), e.getMessage());
        return new ResponseEntity<>(JSONObject.get(e.getMessage()), NOT_FOUND);
    }

    /**
     * Обработка EntityExistsException исключений и проброс текста
     * ошибки в контроллер
     *
     * @param e Exception
     * @return ResponseEntity
     */
    @ResponseStatus(FORBIDDEN)
    @org.springframework.web.bind.annotation.ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<Object> handleEntityExist(EntityExistsException e) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode JSONObject = mapper.createObjectNode();
        JSONObject.put(e.getMessage(), e.getMessage());
        return new ResponseEntity<>(JSONObject.get(e.getMessage()), FORBIDDEN);
    }

    /**
     * Обработка IllegalArgumentException исключений и проброс текста
     * ошибки в контроллер
     *
     * @param e Exception
     * @return ResponseEntity
     */
    @ResponseStatus(BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArg(IllegalArgumentException e) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode JSONObject = mapper.createObjectNode();
        JSONObject.put(e.getMessage(), e.getMessage());
        return new ResponseEntity<>(JSONObject.get(e.getMessage()),BAD_REQUEST);
    }

    /**
     * Обработка RuntimeException исключений и проброс текста
     * ошибки в контроллер
     *
     * @param e Exception
     * @return ResponseEntity
     */
    @ResponseStatus(BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleIllegalArg(RuntimeException e) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode JSONObject = mapper.createObjectNode();
        JSONObject.put(e.getMessage(), e.getMessage());
        return new ResponseEntity<>(JSONObject.get(e.getMessage()), BAD_REQUEST);
    }
}
