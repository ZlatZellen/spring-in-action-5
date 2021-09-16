package com.example.tacos.repository.jdbc;

import java.time.LocalDateTime;
import java.util.Map;

import com.example.tacos.domain.Order;
import com.example.tacos.domain.Taco;
import com.example.tacos.repository.OrderRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcOrderRepository implements OrderRepository {
    private final SimpleJdbcInsert orderInserter;
    private final SimpleJdbcInsert orderTacoInserter;

    public JdbcOrderRepository(JdbcTemplate jdbc) {
        orderInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("taco_orders")
                .usingGeneratedKeyColumns("id");
        orderTacoInserter = new SimpleJdbcInsert(jdbc)
                .withTableName("taco_order_tacos");
    }

    @Override
    public Order save(Order order) {
        long orderId = saveOrderDetails(order);
        order.setId(orderId);

        for (Taco taco : order.getTacos()) {
            saveTacoToOrder(orderId, taco);
        }

        return order;
    }

    private long saveOrderDetails(Order order) {
        order.setCreatedAt(LocalDateTime.now());

        Map<String, ?> values = Map.of(
                "name", order.getName(),
                "street", order.getStreet(),
                "city", order.getCity(),
                "state", order.getState(),
                "zip_code", order.getZipCode(),
                "card_number", order.getCardNumber(),
                "card_expiration_date", order.getCardExpirationDate(),
                "cvv", order.getCvv(),
                "created_at", order.getCreatedAt());

        return orderInserter.executeAndReturnKey(values).longValue();
    }

    private void saveTacoToOrder(long orderId, Taco taco) {
        Map<String, ?> values = Map.of(
                "taco_order_id", orderId,
                "taco_id", taco.getId());
        orderTacoInserter.execute(values);
    }
}
