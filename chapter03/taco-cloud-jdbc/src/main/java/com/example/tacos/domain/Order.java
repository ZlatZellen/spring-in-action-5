package com.example.tacos.domain;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.CreditCardNumber;

@Getter
@Setter
public class Order extends BaseEntity {
    @NotBlank(message = "Name can't be blank")
    @Size(max = 50, message = "Name must be less than 50 characters")
    private String name;

    @NotBlank(message = "Street can't be blank")
    @Size(max = 50, message = "Street must be less than 50 characters")
    private String street;

    @NotBlank(message = "City can't be blank")
    @Size(max = 50, message = "City must be less than 50 characters")
    private String city;

    @NotBlank(message = "State can't be blank")
    @Size(min = 2, max = 2, message = "State must be 2 characters long")
    private String state;

    @NotBlank(message = "Zip code can't be blank")
    @Size(max = 10, message = "Zip code must be less than 10 characters")
    private String zipCode;

    @NotNull(message = "Card number can't be null")
    @CreditCardNumber(message = "Not a valid credit card number")
    private String cardNumber;

    @NotNull(message = "Card expiration date can't be null")
    @Pattern(
            regexp = "^(0[1-9]|1[0-2])([/])([1-9][0-9])$",
            message = "Must be formatted MM/YY")
    private String cardExpirationDate;

    @NotNull(message = "CVV can't be null")
    @Digits(integer = 3, fraction = 0, message = "CVV code must be digits")
    @Size(min = 3, max = 3, message = "CVV code must be 3 characters long")
    private String cvv;

    @NotEmpty(message = "You must add at least 1 taco")
    private List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco) {
        tacos.add(taco);
    }
}
