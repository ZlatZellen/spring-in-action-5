package com.example.tacos.controller;

import javax.validation.Valid;

import com.example.tacos.domain.Order;
import com.example.tacos.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequiredArgsConstructor
@SessionAttributes("order")
@RequestMapping("/orders")
public class OrderController {
    private final OrderRepository orderRepository;

    @GetMapping("/current")
    public String showOrderForm(Model model) {
        if (!model.containsAttribute("order")) {
            return "redirect:/tacos";
        }

        Order order = (Order) model.getAttribute("order");
        if (CollectionUtils.isEmpty(order.getTacos())) {
            return "redirect:/tacos";
        }

        return "orderForm";
    }

    @PostMapping
    public String processOrder(
            @Valid Order order,
            Errors errors,
            SessionStatus sessionStatus) {

        if (errors.hasErrors()) {
            return "orderForm";
        }

        orderRepository.save(order);
        sessionStatus.setComplete();

        return "redirect:/";
    }
}
