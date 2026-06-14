package com.example.clubQR.dto;

public class QrCheckResponse {
    private String fullName;

    public QrCheckResponse() {}

    public QrCheckResponse(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}