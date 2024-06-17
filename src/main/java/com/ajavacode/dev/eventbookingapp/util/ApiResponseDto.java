package com.ajavacode.dev.eventbookingapp.util;

import io.swagger.v3.oas.annotations.media.Schema;

public record ApiResponseDto(
        @Schema(description = "Boolean that indicates if the request was successful or not", example = "true")
        Boolean status,

        @Schema(description = "A message decribing the response or explaining the reason for the response")
        String message,

        @Schema(description = "Object of the resource that was created, updated, or fetched", oneOf = {Void.class})
        Object data) {
}
