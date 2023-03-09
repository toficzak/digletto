package com.github.toficzak.digletto.config;

import com.github.toficzak.digletto.core.exception.IdeaNotFoundException;
import com.github.toficzak.digletto.core.exception.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IdeaNotFoundException.class)
    public ResponseEntity<Object> handleIdeaNotFound(IdeaNotFoundException exception, WebRequest request) {
        Error error = new Error(ErrorCodes.IDEA_NOT_FOUND);
        return handleExceptionInternal(exception, error, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFound(UserNotFoundException exception, WebRequest request) {
        Error error = new Error(ErrorCodes.USER_NOT_FOUND);
        return handleExceptionInternal(exception, error, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}