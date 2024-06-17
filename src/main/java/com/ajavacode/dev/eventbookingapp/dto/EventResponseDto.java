package com.ajavacode.dev.eventbookingapp.dto;

public record EventResponseDto(Long id, String name, String date,
                               Integer availableAttendeesCount,
                               String description, String category) {
}
