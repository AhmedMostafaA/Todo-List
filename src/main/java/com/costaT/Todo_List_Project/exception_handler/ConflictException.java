package com.costaT.Todo_List_Project.exception_handler;

import org.springframework.http.HttpStatus;

public class ConflictException  extends ApiBasicException{
    public ConflictException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.CONFLICT;
    }
}
