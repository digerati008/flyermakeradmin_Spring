package com.project.flyermakeradmin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DataNotInDatabaseException extends RuntimeException {
    public DataNotInDatabaseException(String message) {
        super(message);
    }

    public DataNotInDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}