package com.musalasoft.dev.eventbookingapp.controller;

import com.musalasoft.dev.eventbookingapp.dto.EventDto;
import com.musalasoft.dev.eventbookingapp.model.Event;
import com.musalasoft.dev.eventbookingapp.service.EventService;
import com.musalasoft.dev.eventbookingapp.util.ApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class EventController {

    private final EventService eventService;

    @Operation(description = "This endpoint allows customers to create a new event.")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Created",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDto.class)),
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    @PostMapping
    public ResponseEntity<ApiResponseDto> createEvent(@Valid @RequestBody EventDto eventDto) {
        Event event = eventService.createEvent(eventDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponseDto(true, "Event created successfully", event));
    }



    @Operation(description = "This endpoint allows customers to retrieve all events or search for events by name, date range or category.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    @GetMapping
    public ResponseEntity<ApiResponseDto> searchEvent(@RequestParam(required = false) String name,
                                                      @RequestParam(required = false) String startDate,
                                                      @RequestParam(required = false) String endDate,
                                                      @RequestParam(required = false) String category) {
        List<Event> event = eventService.searchEvent(name, startDate, endDate, category);
        return ResponseEntity.ok(new ApiResponseDto(true, "Events retrieved successfully", event));
    }
}
