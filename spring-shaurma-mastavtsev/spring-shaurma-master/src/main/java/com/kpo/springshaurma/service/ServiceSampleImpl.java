package com.kpo.springshaurma.service;

import com.kpo.springshaurma.model.Ingredient;
import com.kpo.springshaurma.model.ShaurmaOrder;
import com.kpo.springshaurma.repository.IngredientJDBCRepository;
import com.kpo.springshaurma.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceSampleImpl implements ServiceSample {

    private final IngredientJDBCRepository ingredientRepository;
    private final OrderRepository orderRepository;

    public void modifyModel(Model model) {
        List<Ingredient> ingredients = (List<Ingredient>) ingredientRepository.findAll();
        Ingredient.Type[] types = Ingredient.Type.values();
        for (Ingredient.Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
    }

    public void addShaurmaToOrder(ShaurmaOrder shaurmaOrder) {
        orderRepository.save(shaurmaOrder);
    }


    public void modifyModelOld(Model model) {
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("CHEESE", "Сырный", Ingredient.Type.SAUCE),
                new Ingredient("SRSW", "Кисло-сладкий", Ingredient.Type.SAUCE),
                new Ingredient("MAZIK", "Майонез", Ingredient.Type.SAUCE),
                new Ingredient("KTCHUNEZ", "100 Островов", Ingredient.Type.SAUCE)
        );

        Ingredient.Type[] types = Ingredient.Type.values();
        for (Ingredient.Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .toList();
    }
}
