package com.SD_restaurant.SD_restaurant.controller;

import com.SD_restaurant.SD_restaurant.dto.DishDto;
import com.SD_restaurant.SD_restaurant.model.Dish;
import com.SD_restaurant.SD_restaurant.service.DishService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller class for handling dish-related requests.
 */
@RestController
@RequestMapping("/api/dish")
@RequiredArgsConstructor
public class DishController {

    private final DishService dishService;

    /**
     * Handles the HTTP GET request to retrieve a dish by its ID.
     * @param dishId the ID of the dish to retrieve
     * @return the dish with the specified ID
     */
    @GetMapping("/{dishId}")
    public Dish getDishById(@PathVariable String dishId) {
        return dishService.getDishById(dishId);
    }

    /**
     * Handles the HTTP GET request to retrieve all dishes.
     * @return the list of all dishes
     */
    @GetMapping
    public List<Dish> getAllDishes() {
        return dishService.getAllDishes();
    }

    /**
     * Handles the HTTP POST request to create a new dish.
     * @param dishDto the DTO object containing the dish details
     * @return the created dish
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Dish createDish(@RequestBody DishDto dishDto) {
        return dishService.createDish(dishDto);
    }


    /**
     * Handles the HTTP PUT request to update a dish.
     * @param dishId the ID of the dish to update
     * @param dishDto the DTO object containing the updated dish details
     * @return the updated dish
     */
    @PutMapping("/{dishId}")
    public Dish updateDish(@PathVariable String dishId, @RequestBody DishDto dishDto) {
        return dishService.updateDish(dishId, dishDto);
    }

    /**
     * Handles the HTTP DELETE request to delete a dish.
     * @param dishId the ID of the dish to delete
     */
    @DeleteMapping("/{dishId}")
    public void deleteDish(@PathVariable String dishId) {
        dishService.deleteDish(dishId);
    }
}
