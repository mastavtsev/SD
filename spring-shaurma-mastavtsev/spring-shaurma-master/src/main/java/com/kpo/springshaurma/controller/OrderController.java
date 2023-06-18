package com.kpo.springshaurma.controller;

import com.kpo.springshaurma.model.ShaurmaOrder;
import com.kpo.springshaurma.repository.OrderRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
@RequiredArgsConstructor
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    @GetMapping("/current")

    public String displayOrder(Model model) {
        if (!model.containsAttribute("order")) {
            model.addAttribute("order", new ShaurmaOrder());
        }
        return "displayOrder";
    }

    @PostMapping
    public String newOrder(@Valid ShaurmaOrder order, Errors errors,
                           SessionStatus sessionStatus) {

        if (errors.hasErrors()) {
            return "orderForm";
        }

        orderRepository.save(order);

        sessionStatus.setComplete();
        return "redirect:/create";
    }
}