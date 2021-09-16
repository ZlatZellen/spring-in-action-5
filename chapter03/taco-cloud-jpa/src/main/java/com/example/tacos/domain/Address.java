package com.example.tacos.domain;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Address {
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
}
