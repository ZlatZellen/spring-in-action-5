package com.example.tacos.repository.jdbc;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.List;

import com.example.tacos.domain.Taco;
import com.example.tacos.repository.TacoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcTacoRepository implements TacoRepository {
    private final JdbcTemplate jdbc;

    @Override
    public Taco save(Taco taco) {
        long tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);

        for (String ingredientId : taco.getIngredients()) {
            saveIngredientToTaco(tacoId, ingredientId);
        }

        return taco;
    }

    private long saveTacoInfo(Taco taco) {
        taco.setCreatedAt(LocalDateTime.now());

        PreparedStatementCreatorFactory creatorFactory = new PreparedStatementCreatorFactory(
                "INSERT INTO tacos (name, created_at) VALUES (?, ?)",
                Types.VARCHAR,
                Types.TIMESTAMP);
        creatorFactory.setReturnGeneratedKeys(true);

        PreparedStatementCreator statementCreator =
                creatorFactory.newPreparedStatementCreator(List.of(
                        taco.getName(),
                        taco.getCreatedAt()));
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(statementCreator, keyHolder);

        return keyHolder.getKey().longValue();
    }

    private void saveIngredientToTaco(long tacoId, String ingredientId) {
        jdbc.update(
                "INSERT INTO taco_ingredients (taco_id, ingredient_id) VALUES (?, ?)",
                tacoId,
                ingredientId);
    }
}
