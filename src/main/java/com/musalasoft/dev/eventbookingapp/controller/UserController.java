package com.musalasoft.dev.eventbookingapp.controller;

import com.musalasoft.dev.eventbookingapp.dto.UserDto;
import com.musalasoft.dev.eventbookingapp.model.User;
import com.musalasoft.dev.eventbookingapp.service.UserService;
import com.musalasoft.dev.eventbookingapp.util.ApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(description = "This endpoint allows customers to create a new user.")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Created",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponseDto.class)),
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @PostMapping
    public ResponseEntity<ApiResponseDto> createUser(@Valid @RequestBody UserDto payload) {
        User user = userService.createUser(payload);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDto(true, "User created successfully", user));
    }
}
