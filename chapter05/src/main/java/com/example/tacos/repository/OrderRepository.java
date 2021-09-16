package com.example.tacos.repository;

import java.util.List;

import com.example.tacos.domain.persistence.Order;
import com.example.tacos.domain.persistence.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
}
