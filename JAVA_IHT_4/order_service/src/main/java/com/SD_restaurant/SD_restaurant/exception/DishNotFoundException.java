package com.SD_restaurant.SD_restaurant.exception;

/**
 * Custom exception class for DishNotFoundException.
 * This exception is thrown when a dish is not found.
 */
public class DishNotFoundException extends RuntimeException {
    /**
     * Constructs a new DishNotFoundException with the specified error message.
     *
     * @param message the error message
     */
    public DishNotFoundException(String message) {
        super(message);
    }
}
