package com.example.tacos.repository;

import java.util.List;

import com.example.tacos.domain.Ingredient;

public interface IngredientRepository {
    List<Ingredient> findAll();

    Ingredient findById(String id);

    Ingredient save(Ingredient ingredient);
}
