package com.example.tacos.domain.request;

import javax.persistence.Embedded;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.tacos.domain.persistence.Address;
import com.example.tacos.domain.persistence.User;
import com.example.tacos.validation.Password;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
public class RegistrationForm {
    @NotBlank(message = "Username can't be blank")
    @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters long")
    private String username;

    @Password(message = "Password must contain at least one digit, special symbol, "
            + "upper and lower character")
    private String password;

    @NotBlank(message = "Full name can't be blank")
    @Size(max = 50, message = "Full name must be less than 50 characters")
    private String fullName;

    @Valid
    @Embedded
    @NotNull(message = "Address can't be null")
    private Address address;

    @NotNull(message = "Phone number can't be null")
    @Digits(integer = 11, fraction = 0, message = "Phone number must be digits")
    @Size(min = 11, max = 11, message = "Phone number must be 11 characters long")
    private String phone;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(username, passwordEncoder.encode(password), fullName, address, phone);
    }
}
