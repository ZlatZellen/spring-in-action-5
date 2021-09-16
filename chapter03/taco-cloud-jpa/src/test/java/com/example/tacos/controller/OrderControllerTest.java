package com.example.tacos.controller;

import com.example.tacos.domain.Order;
import com.example.tacos.domain.Taco;
import com.example.tacos.repository.IngredientRepository;
import com.example.tacos.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private IngredientRepository ingredientRepository;

    @Test
    public void testRedirectToTacoFormIfSessionEmpty() throws Exception {
        mockMvc
                .perform(get("/orders/current"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tacos"));
    }

    @Test
    public void testShowFormIfSessionWithOrder() throws Exception {
        Order order = new Order();
        order.addTaco(new Taco());

        mockMvc
                .perform(get("/orders/current")
                        .sessionAttr("order", order))
                .andExpect(status().isOk())
                .andExpect(view().name("orderForm"));
    }

    @Test
    public void testFailSaveIfDataInvalid() throws Exception {
        Order order = new Order();
        order.addTaco(new Taco());
        String invalidPayload = "name=James";
        String commonErrorMessage = "Please correct the problems below and resubmit";

        mockMvc
                .perform(post("/orders")
                        .sessionAttr("order", order)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(invalidPayload))
                .andExpect(view().name("orderForm"))
                .andExpect(content().string(containsString(commonErrorMessage)));
    }

    @Test
    public void testSuccessSaveIfDataValid() throws Exception {
        Order order = new Order();
        order.addTaco(new Taco());
        String validPayload = "name=James&address.street=teerts&address.city=ytic&"
                + "address.state=TX&address.zipCode=123&cardNumber=4111111111111111&"
                + "cardExpirationDate=11%2F21&cvv=123";

        when(orderRepository.save(any())).then(a -> a.getArgument(0));

        mockMvc
                .perform(post("/orders")
                        .sessionAttr("order", order)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(validPayload))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(orderRepository, times(1)).save(any());
    }
}
