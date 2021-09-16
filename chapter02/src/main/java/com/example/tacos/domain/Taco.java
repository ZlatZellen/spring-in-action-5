package com.example.tacos.domain;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class Taco {
    @NotBlank(message = "Name can't be blank")
    @Size(min = 3, max = 50, message = "Name length must be between 3 and 50 characters")
    private String name;

    @NotEmpty(message = "Ingredients can't be empty")
    private List<String> ingredients;
}
