package com.SD_restaurant.SD_restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object (DTO) class for Order request.
 * Represents the data structure for creating an order.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private List<DishDto> dishDtoList;
    private String status;

    private String specialDescription;

    private Long userId;
}
