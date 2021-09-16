package com.example.tacos.repository;

import com.example.tacos.domain.persistence.Taco;
import org.springframework.data.repository.CrudRepository;

public interface TacoRepository extends CrudRepository<Taco, Long> {
}
