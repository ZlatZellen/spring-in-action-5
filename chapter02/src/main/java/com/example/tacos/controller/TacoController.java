package com.example.tacos.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.example.tacos.domain.Ingredient;
import com.example.tacos.domain.Taco;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/tacos")
public class TacoController {
    @GetMapping
    public String showTacoForm(Model model) {
        addIngredientsAndTacoToModel(model);
        return "tacoForm";
    }

    @PostMapping
    public String processTaco(@Valid Taco taco, Errors errors, Model model) {
        if (errors.hasErrors()) {
            addIngredientsAndTacoToModel(model);
            return "tacoForm";
        }

        log.info("Processing taco: " + taco);
        return "redirect:/orders/current";
    }

    private void addIngredientsAndTacoToModel(Model model) {
        List<Ingredient> ingredients = List.of(
                new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE));

        groupByType(ingredients).forEach((k, v) -> {
            model.addAttribute(k.toString().toLowerCase(), v);
        });

        model.asMap().putIfAbsent("taco", new Taco());
    }

    private Map<Ingredient.Type, List<Ingredient>> groupByType(List<Ingredient> ingredients) {
        return ingredients.stream().collect(Collectors.groupingBy(Ingredient::getType));
    }
}
