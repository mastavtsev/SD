package io.bootify.kpo.exception_controller;


import io.bootify.kpo.exception.InvalidEmailException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UnauthExceptionContoller {

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<String> handleUnauthException(InvalidEmailException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}

