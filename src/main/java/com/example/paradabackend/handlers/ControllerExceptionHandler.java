package com.example.paradabackend.handlers;

import javassist.NotFoundException;
import org.jboss.jandex.Index;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public Exception handleNotFoundException(NotFoundException e) {
        return e;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Exception handleIllegalArgumentException(IllegalArgumentException e) {
        return e;
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public Exception handleIndexOutOfBoundsException(IndexOutOfBoundsException e) {
        return e;
    }
}