package com.iarasantos.teacherservice.exceptions;

import com.iarasantos.teacherservice.model.ErrorMessage;
import jakarta.validation.ConstraintViolationException;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleNotValidException(MethodArgumentNotValidException e) {
        var errors = e.getAllErrors();

        ErrorMessage message = null;
        if (errors != null && !errors.isEmpty()) {
            message  = new ErrorMessage(400, errors.get(0).getDefaultMessage());
            return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
        }
        message = new ErrorMessage(400, "Bad Request");
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NoSuchElementException.class, NumberFormatException.class})
    public ResponseEntity<ErrorMessage> handleNoSuchElementException(Exception e) {
        ErrorMessage message = new ErrorMessage(400, "Not Found");
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> handleConstraintViolationException(Exception e) {
        ErrorMessage message = new ErrorMessage(400, "Bad Request");
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ErrorMessage> handleNotSupportedException(Exception e) {
        ErrorMessage message = new ErrorMessage(400, "Bad Request");
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handleIllegalArgumentException(IllegalArgumentException e) {
        ErrorMessage message = new ErrorMessage(400, e.getMessage());
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
    }
}
