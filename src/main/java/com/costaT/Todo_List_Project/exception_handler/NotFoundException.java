package com.costaT.Todo_List_Project.exception_handler;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiBasicException {

    public NotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }
}
