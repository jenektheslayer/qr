package com.example.clubQR.exception;

import com.example.clubQR.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ParticipantNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleParticipantNotFound(ParticipantNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, "Not Found", exception.getMessage()));
    }
    @ExceptionHandler(QrCodeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerQrCodeNotFound(QrCodeNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(404, "Not Found", exception.getMessage()));
    }
}
