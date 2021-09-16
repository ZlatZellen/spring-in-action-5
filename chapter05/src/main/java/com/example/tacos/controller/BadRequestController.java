package com.example.tacos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/exception")
public class BadRequestController {
    @GetMapping
    public String throwException() {
        throw new RuntimeException("An exception was thrown to test the page error behavior");
    }
}
