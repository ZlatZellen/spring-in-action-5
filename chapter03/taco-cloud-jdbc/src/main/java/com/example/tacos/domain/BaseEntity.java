package com.example.tacos.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseEntity {
    private Long id;
    private LocalDateTime createdAt;
}
