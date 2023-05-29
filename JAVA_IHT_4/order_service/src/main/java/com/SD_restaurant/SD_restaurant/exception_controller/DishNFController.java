package com.SD_restaurant.SD_restaurant.exception_controller;

import com.SD_restaurant.SD_restaurant.exception.DishNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DishNFController {
    /**
     * Exception handler for DishNotFoundException.
     * Handles the exception and returns an appropriate response entity with a status code and error message.
     *
     * @param ex the OrderNotFoundException
     * @return the ResponseEntity with the error message and status code
     */
    @ExceptionHandler(DishNotFoundException.class)
    public ResponseEntity<String> handleInvalidEmailException(DishNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}


