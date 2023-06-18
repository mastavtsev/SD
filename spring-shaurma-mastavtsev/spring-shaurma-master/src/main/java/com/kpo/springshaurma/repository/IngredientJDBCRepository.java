package com.kpo.springshaurma.repository;

import com.kpo.springshaurma.model.Ingredient;

import java.util.Optional;

public interface IngredientJDBCRepository {
    Iterable<Ingredient> findAll();

    Optional<Ingredient> findById(String id);

    Ingredient save(Ingredient ingredient);
}
