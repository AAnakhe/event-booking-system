package com.ajavacode.dev.eventbookingapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record UserDto(
        @NotBlank(message = "Name cannot be blank")
        @Length(max = 100)
        String name,
        @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
        String email,

        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@$!%*?&]).{8,}$",
                message = "Password must be at least 8 characters long and a mixture of numbers, special characters," +
                        "upper case and lower case"
        )
        String password) {
}
