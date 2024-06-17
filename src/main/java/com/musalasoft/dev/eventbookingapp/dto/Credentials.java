package com.musalasoft.dev.eventbookingapp.dto;

import jakarta.validation.constraints.NotBlank;

public record Credentials(@NotBlank(message = "must be provided")String email,
                          @NotBlank(message = "must be provided")String password) {
}
