package com.ajavacode.dev.eventbookingapp.controller;

import com.ajavacode.dev.eventbookingapp.dto.Credentials;
import com.ajavacode.dev.eventbookingapp.model.User;
import com.ajavacode.dev.eventbookingapp.service.UserService;
import com.ajavacode.dev.eventbookingapp.service.security.JwtService;
import com.ajavacode.dev.eventbookingapp.util.ApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;


    @Operation(description = "This endpoint allows users to authenticate and receive a Bearer token.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "OK",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ApiResponseDto.class)),
                            }),
            @ApiResponse(responseCode = "401", description = "Unauthorised")
    })
    @PostMapping()
    public ResponseEntity<ApiResponseDto> authenticateUser(@RequestBody Credentials payload) {
        User user = userService.findUserByEmail(payload.email());

        if (user != null && passwordEncoder.matches(payload.password(), user.getPassword())){
            String token = jwtService.generateAccessJwt(payload.email());
            return ResponseEntity.ok(new ApiResponseDto(true, "User authenticated successfully", token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponseDto(false, "Invalid credentials", null));
    }
}
