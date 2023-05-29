package com.SD_restaurant.SD_restaurant.model.repository;

import com.SD_restaurant.SD_restaurant.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DIshRepository extends JpaRepository<Dish, Long> {

    /*
     * findById() method:
     * This method finds a dish by its ID.
     * It takes a Long value representing the dish ID as input.
     * It returns an Optional object that may contain the dish if found, or an empty Optional if not found.
     */
    Optional<Dish> findById(Long id);

    /*
     * findAllByQuantityGreaterThan() method:
     * This method finds all dishes whose quantity is greater than a specified value.
     * It takes an int value representing the minimum quantity as input.
     * It returns a List containing all the dishes that meet the specified condition.
     */
    List<Dish> findAllByQuantityGreaterThan(int i);
}
