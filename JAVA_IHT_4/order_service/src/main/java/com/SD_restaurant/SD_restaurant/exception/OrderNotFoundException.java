package com.SD_restaurant.SD_restaurant.exception;

/**
 * Custom exception class for DishNotFoundException.
 * This exception is thrown when a dish is not found.
 */
public class OrderNotFoundException extends RuntimeException {
    /**
     * Constructs a new OrderNotFoundException with the specified error message.
     *
     * @param message the error message
     */
    public OrderNotFoundException(String message) {
        super(message);
    }
}
