package com.SD_restaurant.SD_restaurant.service;

import com.SD_restaurant.SD_restaurant.dto.DishDto;
import com.SD_restaurant.SD_restaurant.exception.DishNotFoundException;
import com.SD_restaurant.SD_restaurant.model.Dish;
import com.SD_restaurant.SD_restaurant.model.repository.DIshRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DishService {
    private final DIshRepository dishRepository;

    /**
     * Get a dish by its ID.
     *
     * @param dishId the ID of the dish
     * @return the found dish
     * @throws DishNotFoundException if the dish is not found
     */
    public Dish getDishById(String dishId) {
        return dishRepository.findById(Long.valueOf(dishId))
                .orElseThrow(() -> new DishNotFoundException("Dish not found with ID: " + dishId));
    }

    /**
     * Get all dishes.
     *
     * @return the list of all dishes
     */
    public List<Dish> getAllDishes() {
        return dishRepository.findAll();
    }

    /**
     * Create a new dish.
     *
     * @param dishDto the dish data transfer object
     * @return the created dish
     */
    public Dish createDish(DishDto dishDto) {
        Dish dish = new Dish();
        dish.setName(dishDto.getName());
        dish.setDescription(dishDto.getDescription());
        dish.setQuantity(dishDto.getQuantity());
        dish.setPrice(dishDto.getPrice());
        return dishRepository.save(dish);
    }

    /**
     * Update an existing dish.
     *
     * @param dishId   the ID of the dish to update
     * @param dishDto  the updated dish data transfer object
     * @return the updated dish
     * @throws DishNotFoundException if the dish is not found
     */
    public Dish updateDish(String dishId, DishDto dishDto) {
        Dish dish = getDishById(dishId);
        dish.setName(dishDto.getName());
        dish.setDescription(dishDto.getDescription());
        dish.setQuantity(dishDto.getQuantity());
        dish.setPrice(dishDto.getPrice());
        return dishRepository.save(dish);
    }

    /**
     * Delete a dish by its ID.
     *
     * @param dishId the ID of the dish to delete
     */
    public void deleteDish(String dishId) {
        dishRepository.deleteById(Long.valueOf(dishId));
    }
}
