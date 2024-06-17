package com.ajavacode.dev.eventbookingapp.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record EventDto(
        @NotBlank(message = "Name cannot be blank")
        @Length(max = 100)
        String name,
        @NotBlank(message = "Date cannot be blank")
        String date,
        @NotNull(message = "Available Attendees Count cannot be blank")
        @Max(1000)
        Integer availableAttendeesCount,
        @NotBlank(message = "Description cannot be blank")
        @Length(max = 500)
        String description,
        @Enumerated(EnumType.STRING)
        String category) {
}
