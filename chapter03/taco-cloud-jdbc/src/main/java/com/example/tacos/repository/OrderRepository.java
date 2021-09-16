package com.example.tacos.repository;

import com.example.tacos.domain.Order;

public interface OrderRepository {
    Order save(Order order);
}
