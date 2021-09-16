package com.example.tacos.controller;

import com.example.tacos.domain.persistence.User;
import com.example.tacos.repository.IngredientRepository;
import com.example.tacos.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(RegistrationController.class)
public class RegistrationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredientRepository ingredientRepository;

    @MockBean
    private UserService userService;

    @Test
    public void testShowRegistrationForm() throws Exception {
        mockMvc
                .perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"));
    }

    @Test
    public void testFailSaveIfDataInvalid() throws Exception {
        String invalidPayload = "username=user&address.street=teerts&address.city=ytic&"
                + "address.state=t&address.zipCode=123456&phone=123";
        String passwordErrorMessage = "Password must contain at least one digit, special "
                + "symbol, upper and lower character";
        String stateErrorMessage = "State must be 2 characters long";
        String phoneErrorMessage = "Phone number must be 11 characters long";

        mockMvc
                .perform(post("/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(invalidPayload))
                .andExpect(view().name("registration"))
                .andExpect(content().string(allOf(
                        containsString(passwordErrorMessage),
                        containsString(stateErrorMessage),
                        containsString(phoneErrorMessage))));
    }

    @Test
    public void testFailSaveIfUserExist() throws Exception {
        String invalidPayload = "username=test&password=Qwerty1!&fullName=test&"
                + "address.street=test&address.city=test&address.state=tx&address.zipCode=12345&"
                + "phone=12345678901";
        String userExistErrorMessage = "User with this username exists";

        when(userService.existsByUsername(anyString())).thenReturn(true);

        mockMvc
                .perform(post("/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(invalidPayload))
                .andExpect(view().name("registration"))
                .andExpect(content().string(containsString(userExistErrorMessage)));

        verify(userService, times(1)).existsByUsername("test");
    }

    @Test
    public void testSuccessSaveIfDataValid() throws Exception {
        String validPayload = "username=test&password=Qwerty1!&fullName=test&"
                + "address.street=test&address.city=test&address.state=tx&address.zipCode=12345&"
                + "phone=12345678901";

        mockMvc
                .perform(post("/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(validPayload))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        verify(userService, times(1)).save(any(User.class));
    }
}
