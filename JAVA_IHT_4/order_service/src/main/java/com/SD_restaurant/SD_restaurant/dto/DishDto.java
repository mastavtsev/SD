package com.SD_restaurant.SD_restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


/**
 * Data Transfer Object (DTO) class for Dish.
 * Represents the data structure for transferring dish information.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishDto {
    private Long id;

    private String name;
    private BigDecimal price;
    private Integer quantity;
    private String description;
}
