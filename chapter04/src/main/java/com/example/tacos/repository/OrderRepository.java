package com.example.tacos.repository;

import com.example.tacos.domain.persistence.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
