package com.example.tacos.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.example.tacos.domain.Ingredient;
import com.example.tacos.domain.Order;
import com.example.tacos.domain.Taco;
import com.example.tacos.repository.IngredientRepository;
import com.example.tacos.repository.TacoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequiredArgsConstructor
@SessionAttributes("order")
@RequestMapping("/tacos")
public class TacoController {
    private final IngredientRepository ingredientRepository;
    private final TacoRepository tacoRepository;

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @GetMapping
    public String showTacoForm(Model model) {
        addIngredientsAndTacoToModel(model);
        return "tacoForm";
    }

    @PostMapping
    public String processTaco(
            @Valid Taco taco,
            Errors errors,
            Model model,
            @ModelAttribute(binding = false) Order order) {

        if (errors.hasErrors()) {
            addIngredientsAndTacoToModel(model);
            return "tacoForm";
        }

        Taco savedTaco = tacoRepository.save(taco);
        order.addTaco(savedTaco);

        return "redirect:/orders/current";
    }

    private void addIngredientsAndTacoToModel(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(ingredients::add);

        groupByType(ingredients).forEach((k, v) -> {
            model.addAttribute(k.toString().toLowerCase(), v);
        });

        if (!model.containsAttribute("taco")) {
            model.addAttribute("taco", new Taco());
        }
    }

    private Map<Ingredient.Type, List<Ingredient>> groupByType(List<Ingredient> ingredients) {
        return ingredients.stream().collect(Collectors.groupingBy(Ingredient::getType));
    }
}
