package com.example.tacos.controller;

import java.util.List;

import com.example.tacos.domain.Ingredient;
import com.example.tacos.repository.IngredientRepository;
import com.example.tacos.repository.TacoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(TacoController.class)
public class TacoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TacoRepository tacoRepository;

    @MockBean
    private IngredientRepository ingredientRepository;

    private List<Ingredient> ingredients;

    @BeforeEach
    public void initIngredientRepository() {
        ingredients = List.of(
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

        when(ingredientRepository.findAll()).thenReturn(ingredients);
    }

    @Test
    public void testShowTacoForm() throws Exception {
        mockMvc
                .perform(get("/tacos"))
                .andExpect(status().isOk())
                .andExpect(view().name("tacoForm"))
                .andExpect(model().attributeExists("taco", "order"))
                .andExpect(model().attribute("wrap", ingredients.subList(0, 2)))
                .andExpect(model().attribute("protein", ingredients.subList(2, 4)))
                .andExpect(model().attribute("veggies", ingredients.subList(4, 6)))
                .andExpect(model().attribute("cheese", ingredients.subList(6, 8)))
                .andExpect(model().attribute("sauce", ingredients.subList(8, 10)));

        verify(ingredientRepository, times(1)).findAll();
    }

    @Test
    public void testFailSaveIfDataInvalid() throws Exception {
        String invalidPayload = "ingredients=FLTO&ingredients=CHED&name=tc";
        String tacoNameErrorMessage = "Name length must be between 3 and 50 characters";
        String commonErrorMessage =
                "Make sure you choose the ingredients and there are no problems with taco name";

        mockMvc
                .perform(post("/tacos")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(invalidPayload))
                .andExpect(view().name("tacoForm"))
                .andExpect(model().attributeExists("taco", "order"))
                .andExpect(model().attribute("wrap", ingredients.subList(0, 2)))
                .andExpect(model().attribute("protein", ingredients.subList(2, 4)))
                .andExpect(model().attribute("veggies", ingredients.subList(4, 6)))
                .andExpect(model().attribute("cheese", ingredients.subList(6, 8)))
                .andExpect(model().attribute("sauce", ingredients.subList(8, 10)))
                .andExpect(content().string(allOf(
                        containsString(tacoNameErrorMessage),
                        containsString(commonErrorMessage))));

        verify(ingredientRepository, times(1)).findAll();
    }

    @Test
    public void testSuccessSaveIfDataValid() throws Exception {
        String validPayload = "ingredients=FLTO&ingredients=CHED&name=taco";

        when(tacoRepository.save(any())).then(a -> a.getArgument(0));

        mockMvc
                .perform(post("/tacos")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(validPayload))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/orders/current"));

        verify(tacoRepository, times(1)).save(any());
    }
}
