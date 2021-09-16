package com.example.tacos.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tacos")
public class Taco extends BaseEntity {
    @NotBlank(message = "Name can't be blank")
    @Size(min = 3, max = 50, message = "Name length must be between 3 and 50 characters")
    private String name;

    @ManyToMany
    @JoinTable(name = "taco_ingredients")
    @NotEmpty(message = "Ingredients can't be empty")
    private List<Ingredient> ingredients;
}
