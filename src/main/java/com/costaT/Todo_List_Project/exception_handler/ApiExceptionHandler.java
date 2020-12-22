package com.costaT.Todo_List_Project.exception_handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(ApiBasicException.class)
    public ResponseEntity<ErrorMessage> handleAPIException(ApiBasicException ex , WebRequest request){
        ErrorMessage details = new ErrorMessage(ex.getMessage(),request.getDescription(false));
        return new ResponseEntity<>(details,ex.getStatusCode());
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorMessage> handleAll(ApiBasicException ex , WebRequest request){
//        ErrorMessage details = new ErrorMessage(ex.getMessage(),request.getDescription(false));
//        return new ResponseEntity<>(details,ex.getStatusCode());
//    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ValidationErrorMessage validationError = new ValidationErrorMessage();
        validationError.setUri(request.getDescription(false));
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        for (FieldError f: fieldErrors)
        {
            validationError.addError(f.getDefaultMessage());
        }

        return new ResponseEntity<>(validationError,HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorMessage error = new ErrorMessage();
        error.setUri(request.getDescription(false));
        error.setMessage(ex.getParameterName()+" Parameter is Missing!");

        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorMessage error = new ErrorMessage();
        error.setUri(request.getDescription(false));
        error.setMessage(ex.getMethod()+" Method is not supported for this Requist!");

        return new ResponseEntity<>(error,HttpStatus.METHOD_NOT_ALLOWED);
    }
}