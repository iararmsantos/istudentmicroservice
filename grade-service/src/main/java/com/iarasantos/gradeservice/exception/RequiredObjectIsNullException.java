package com.iarasantos.gradeservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredObjectIsNullException extends RuntimeException{

    private final static long serialVersionUID = 1L;

    public RequiredObjectIsNullException(String ex) {
        super((ex));
    }

    public RequiredObjectIsNullException() {
        super(("It is not allowed to persist a null object."));
    }
}