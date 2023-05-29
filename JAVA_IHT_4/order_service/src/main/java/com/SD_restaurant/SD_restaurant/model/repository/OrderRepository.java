package com.SD_restaurant.SD_restaurant.model.repository;

import com.SD_restaurant.SD_restaurant.model.Dish;
import com.SD_restaurant.SD_restaurant.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    /*
     * findById() method:
     * This method finds an order by its ID.
     * It takes a Long value representing the order ID as input.
     * It returns an Optional object that may contain the order if found, or an empty Optional if not found.
     */
    Optional<Order> findById(Long id);
}
