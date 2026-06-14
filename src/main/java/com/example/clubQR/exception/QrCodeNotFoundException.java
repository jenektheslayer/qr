package com.example.clubQR.exception;

public class QrCodeNotFoundException extends RuntimeException {

    public QrCodeNotFoundException(String message) {
        super(message);
    }
}
