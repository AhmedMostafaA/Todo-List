package com.costaT.Todo_List_Project.exception_handler;

import org.springframework.http.HttpStatus;

public abstract class ApiBasicException extends RuntimeException {
    public ApiBasicException(String message) {
        super(message);
    }
    public abstract HttpStatus getStatusCode();
}
