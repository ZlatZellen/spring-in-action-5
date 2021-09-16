package com.example.tacos.controller;

import javax.validation.Valid;

import com.example.tacos.domain.request.RegistrationForm;
import com.example.tacos.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @ModelAttribute(name = "registrationForm")
    public RegistrationForm registrationForm() {
        return new RegistrationForm();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping
    public String processRegistration(@Valid RegistrationForm form, BindingResult errors) {
        if (errors.hasErrors()) {
            return "registration";
        } else if (userService.existsByUsername(form.getUsername())) {
            FieldError fieldError = new FieldError(
                    "registrationForm",
                    "username",
                    "User with this username exists");
            errors.addError(fieldError);
            return "registration";
        }

        userService.save(form.toUser(passwordEncoder));

        return "redirect:/login";
    }
}
