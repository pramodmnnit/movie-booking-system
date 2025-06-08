package com.movie.booking.system.exception;

import com.movie.booking.system.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ApiResponse.ErrorDetails errorDetails = new ApiResponse.ErrorDetails();
        errorDetails.setCode("VALIDATION_ERROR");
        errorDetails.setDetails(errors.toString());

        return ResponseEntity.badRequest()
                .body(ApiResponse.error("Validation failed", errorDetails));
    }

    @ExceptionHandler(InvalidBookingException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidBookingException(InvalidBookingException ex) {
        return ResponseEntity.badRequest()
                .body(ApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(InvalidSeatException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidSeatException(InvalidSeatException ex) {
        return ResponseEntity.badRequest()
                .body(ApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(UnavailableSeatException.class)
    public ResponseEntity<ApiResponse<Void>> handleUnavailableSeatException(UnavailableSeatException ex) {
        return ResponseEntity.badRequest()
                .body(ApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
        return ResponseEntity.internalServerError()
                .body(ApiResponse.error("An unexpected error occurred"));
    }
} 