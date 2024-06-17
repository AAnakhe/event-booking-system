package com.ajavacode.dev.eventbookingapp.controller;

import com.ajavacode.dev.eventbookingapp.dto.TicketRequest;
import com.ajavacode.dev.eventbookingapp.model.Reservation;
import com.ajavacode.dev.eventbookingapp.service.ReservationService;
import com.ajavacode.dev.eventbookingapp.util.ApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
@SecurityRequirement(name = "bearerAuth")
public class TicketReservationController {

    private final ReservationService reservationService;

    @Operation(description = "This endpoint allows customers to reserve tickets for an event.")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Created",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDto.class)),
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping("/{eventId}/tickets")
    public ResponseEntity<ApiResponseDto> reserveTickets(@PathVariable Integer eventId, @RequestBody TicketRequest payload) {
        Reservation reservation = reservationService.saveReservation(eventId, payload);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponseDto(true, "Successfully reserved tickets", reservation ));
    }


    @Operation(description = "This endpoint allows customers to view their booked events.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    @GetMapping("/my-events")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponseDto> viewBookedReservations() {
        List<Reservation> reservation = reservationService.viewBookedEvents();
        return ResponseEntity.ok(new ApiResponseDto(true, "Booked events retrieved successfully", reservation));
    }

    @Operation(description = "This endpoint allows customers to cancel their reservations.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    @DeleteMapping("/{reservationId}/tickets")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponseDto> cancelReservation(@PathVariable Integer reservationId) {
        reservationService.cancelReservations(reservationId);
        return ResponseEntity.ok(new ApiResponseDto(true, "Reservation cancelled successfully", null));
    }


}
