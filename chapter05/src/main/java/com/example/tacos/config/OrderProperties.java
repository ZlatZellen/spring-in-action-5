package com.example.tacos.config;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Component
@Validated
@ConfigurationProperties(prefix = "taco.orders")
public class OrderProperties {
    @Range(min = 5, max = 25, message = "Page size must be between 5 and 25")
    private int pageSize = 20;
}
