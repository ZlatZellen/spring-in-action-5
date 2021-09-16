package com.example.tacos.repository;

import com.example.tacos.domain.persistence.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
