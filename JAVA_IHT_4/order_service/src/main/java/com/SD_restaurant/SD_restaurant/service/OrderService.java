package com.SD_restaurant.SD_restaurant.service;

import com.SD_restaurant.SD_restaurant.dto.DishDto;
import com.SD_restaurant.SD_restaurant.dto.OrderRequest;
import com.SD_restaurant.SD_restaurant.exception.OrderNotFoundException;
import com.SD_restaurant.SD_restaurant.model.Dish;
import com.SD_restaurant.SD_restaurant.model.Order;
import com.SD_restaurant.SD_restaurant.model.repository.DIshRepository;
import com.SD_restaurant.SD_restaurant.model.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final DIshRepository dishRepository;

    /**
     * Place a new order.
     *
     * @param orderRequest the order request
     */
    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();

        order.setOrderNumber(UUID.randomUUID().toString());

        order.setOrderNumber(UUID.randomUUID().toString());
        order.setUserId(orderRequest.getUserId());
        order.setSpecialDescription(orderRequest.getSpecialDescription());
        order.setStatus("pending");


        List<Dish> dishItems = new ArrayList<>();
        for (DishDto dishDto : orderRequest.getDishDtoList()) {
            Dish dish = mapToDto(dishDto);
            dishItems.add(dish);
        }

        order.setDishesList(dishItems);

        orderRepository.save(order);
    }


    /**
     * Map a DishDto to a Dish entity.
     *
     * @param dishDto the dish data transfer object
     * @return the mapped Dish entity
     */
    private Dish mapToDto(DishDto dishDto) {
        Dish dish = new Dish();

        dish.setPrice(dishDto.getPrice());
        dish.setDescription(dishDto.getDescription());
        dish.setName(dishDto.getName());
        dish.setQuantity(dishDto.getQuantity());

        return dish;
    }

    /**
     * Get an order by its ID.
     *
     * @param orderId the ID of the order
     * @return the found order
     * @throws OrderNotFoundException if the order is not found
     */
    public Order getOrderById(String orderId) {
        return orderRepository.findById(Long.valueOf(orderId))
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));
    }

    /**
     * Get the menu, which includes dishes with quantity greater than 0 (available for order).
     *
     * @return the list of available dishes
     */
    public List<Dish> getMenu() {
        return dishRepository.findAllByQuantityGreaterThan(0);
    }
}
