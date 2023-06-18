package com.kpo.springshaurma.converter;

import com.kpo.springshaurma.repository.IngredientJDBCRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import com.kpo.springshaurma.model.Ingredient;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class IngredientToStringConverter implements Converter<String, Ingredient> {

    private final IngredientJDBCRepository ingredientRepo;

    @Override
    public Ingredient convert(String id) {
        return ingredientRepo.findById(id).orElse(null);
    }



/* ----------------------- Old Version -----------------------*/

    private final Map<String, Ingredient> ingredientMap = new HashMap<>();

    public void IngredientToStringConverterOld() {
        ingredientMap.put("CHEESE", new Ingredient("CHEESE", "Сырный", Ingredient.Type.SAUCE));
        ingredientMap.put("SRSW", new Ingredient("SRSW", "Кисло-сладкий", Ingredient.Type.SAUCE));
        ingredientMap.put("MAZIK", new Ingredient("MAZIK", "Майонез", Ingredient.Type.SAUCE));
        ingredientMap.put("KTCHUNEZ", new Ingredient("KTCHUNEZ", "100 Островов", Ingredient.Type.SAUCE));
    }

    public Ingredient convertOld(String id) {
        return ingredientMap.get(id);
    }
}
