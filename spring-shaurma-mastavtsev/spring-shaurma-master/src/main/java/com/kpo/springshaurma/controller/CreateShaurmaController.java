package com.kpo.springshaurma.controller;

import com.kpo.springshaurma.model.Shaurma;
import com.kpo.springshaurma.model.ShaurmaOrder;
import com.kpo.springshaurma.service.ServiceSample;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/create")
@SessionAttributes("order")
@RequiredArgsConstructor
public class CreateShaurmaController {

    private final ServiceSample serviceSample;

    @ModelAttribute
    public void addDataToModel(Model model) {
        serviceSample.modifyModel(model);
    }

    @ModelAttribute(name = "order")
    public ShaurmaOrder order() {
        return new ShaurmaOrder();
    }

    @ModelAttribute(name = "shaurma")
    public Shaurma shaurma() {
        return new Shaurma();
    }

    @GetMapping
    public String getForm() {
        return "shaurma";
    }

    @PostMapping
    public String createShaurma(@Valid Shaurma shaurma, Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "shaurma";
        }

        ShaurmaOrder order;
        if (!model.containsAttribute("order")) {
            order = new ShaurmaOrder();
            model.addAttribute("order", order);
        } else {
            order = (ShaurmaOrder) model.getAttribute("order");
        }

        Objects.requireNonNull(order).addShaurma(shaurma);

        if (log.isInfoEnabled()) {
            log.info("Added shaurma {}", shaurma);
        }

        serviceSample.addShaurmaToOrder(order);

        return "redirect:/orders/current";
    }
}
