package com.ajavacode.dev.eventbookingapp.exceptions;

import com.ajavacode.dev.eventbookingapp.util.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        ApiResponseDto response = new ApiResponseDto(false, "Validation errors", errors);
        return ResponseEntity.badRequest().body(response);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseDto> handleIllegalArgumentException(IllegalArgumentException e) {
        ApiResponseDto response = new ApiResponseDto(
                false,
                e.getMessage(),
                null
        );
        return ResponseEntity.internalServerError().body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseDto> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiResponseDto response = new ApiResponseDto(false,
                ex.getMessage(),
                null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponseDto> handleUnauthorizedException(UnauthorizedException ex) {
        ApiResponseDto response = new ApiResponseDto(false,
                ex.getMessage(),
                null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(TokenVerificationException.class)
    public ResponseEntity<ApiResponseDto> handleTokenVerificationException(TokenVerificationException ex) {
        ApiResponseDto response = new ApiResponseDto(false,
                ex.getMessage(),
                null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EventCreationException.class)
    public ResponseEntity<ApiResponseDto> handleEventCreationException(EventCreationException ex) {
        ApiResponseDto response = new ApiResponseDto(false,
                ex.getMessage(),
                null);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiResponseDto> handleInvalidRequestException(InvalidRequestException ex) {
        ApiResponseDto response = new ApiResponseDto(false, ex.getMessage(), null);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(UserCreationException.class)
    public ResponseEntity<ApiResponseDto> handleUserCreationException(UserCreationException ex) {
        ApiResponseDto response = new ApiResponseDto(
                false,
                ex.toString(),
                null
        );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}