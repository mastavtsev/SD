package com.SD_restaurant.SD_restaurant.controller;

import com.SD_restaurant.SD_restaurant.dto.OrderRequest;
import com.SD_restaurant.SD_restaurant.model.Dish;
import com.SD_restaurant.SD_restaurant.model.Order;
import com.SD_restaurant.SD_restaurant.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling order-related requests.
 */
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {


    private final OrderService orderService;

    /**
     * Handles the HTTP POST request to create a new order.
     * @param orderRequest the request object containing the order details
     * @return a success message if the order is placed successfully
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        orderService.placeOrder(orderRequest);
        return "Order placed successfully!";
    }

    /**
     * Handles the HTTP GET request to retrieve an order by its ID.
     * @param orderId the ID of the order to retrieve
     * @return the order with the specified ID
     */
    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable String orderId) {
        return orderService.getOrderById(orderId);
    }

    /**
     * Handles the HTTP GET request to retrieve the menu, which contains a list of available dishes.
     * @return the list of available dishes
     */
    @GetMapping("/menu")
    public List<Dish> getMenu() {
        return orderService.getMenu();
    }

}
