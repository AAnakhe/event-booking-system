package com.musalasoft.dev.eventbookingapp.controller;

import com.musalasoft.dev.eventbookingapp.schedular.EventNotificationScheduler;
import com.musalasoft.dev.eventbookingapp.util.ApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("notifications")
@SecurityRequirement(name = "bearerAuth")
public class NotificationController {

    private final EventNotificationScheduler scheduler;

    @Operation(description = "This endpoint allows an admin to manually trigger notification upcoming events and log the event")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/trigger-notification")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponseDto> triggerNotification() {
        scheduler.sendUpcomingEventNotifications();
        return ResponseEntity.ok(new ApiResponseDto(true, "Notification triggered successfully", null));
    }
}

