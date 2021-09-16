package com.example.tacos.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.example.tacos.domain.Ingredient;
import com.example.tacos.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcIngredientRepository implements IngredientRepository {
    private final JdbcTemplate jdbc;

    @Override
    public List<Ingredient> findAll() {
        return jdbc.query(
                "SELECT id, name, type FROM ingredients",
                this::mapRowToIngredient);
    }

    @Override
    public Ingredient findById(String id) {
        return jdbc.queryForObject(
                "SELECT id, name, type FROM ingredients WHERE id = ?",
                this::mapRowToIngredient,
                id);
    }

    private Ingredient mapRowToIngredient(ResultSet resultSet, int rowNum) throws SQLException {
        Ingredient.Type type = Ingredient.Type.valueOf(resultSet.getString("type"));
        return new Ingredient(
                resultSet.getString("id"),
                resultSet.getString("name"),
                type);
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        jdbc.update(
                "INSERT INTO ingredients (id, name, type) VALUES (?, ?, ?)",
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getType());
        return ingredient;
    }
}
