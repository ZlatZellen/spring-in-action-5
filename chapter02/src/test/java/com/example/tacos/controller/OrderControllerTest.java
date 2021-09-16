package com.example.tacos.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
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

    @Test
    public void testShowOrderForm() throws Exception {
        mockMvc
                .perform(get("/orders/current"))
                .andExpect(status().isOk())
                .andExpect(view().name("orderForm"));
    }

    @Test
    public void testFailSaveIfDataInvalid() throws Exception {
        String invalidPayload = "name=James";
        String commonErrorMessage = "Please correct the problems below and resubmit";

        mockMvc
                .perform(post("/orders")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(invalidPayload))
                .andExpect(view().name("orderForm"))
                .andExpect(content().string(containsString(commonErrorMessage)));
    }

    @Test
    public void testSuccessSaveIfDataValid() throws Exception {
        String validPayload = "name=name&street=street&city=city&"
                + "state=AK&zipCode=99501&cardNumber=4111111111111111&"
                + "cardExpirationDate=12%2F24&cvv=123";

        mockMvc
                .perform(post("/orders")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(validPayload))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
