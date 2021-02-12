package com.project.flyermakeradmin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class NotValidInputException extends RuntimeException {
    public NotValidInputException(String message) {
        super(message);
    }
    public NotValidInputException(String message, Throwable cause) {
        super(message, cause);
    }
}
