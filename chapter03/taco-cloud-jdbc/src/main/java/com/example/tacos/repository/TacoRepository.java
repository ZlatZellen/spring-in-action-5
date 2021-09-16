package com.example.tacos.repository;

import com.example.tacos.domain.Taco;

public interface TacoRepository {
    Taco save(Taco taco);
}
