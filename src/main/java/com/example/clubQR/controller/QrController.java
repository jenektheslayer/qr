package com.example.clubQR.controller;


import com.example.clubQR.dto.QrCheckRequest;
import com.example.clubQR.dto.QrCheckResponse;
import com.example.clubQR.service.QrCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/qr")
public class QrController {
    private final QrCodeService qrCodeService;

    public QrController(QrCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }
    @PostMapping("/check")
    public ResponseEntity<QrCheckResponse> checkQrCode (@RequestBody QrCheckRequest request) {
        UUID qrUuid = UUID.fromString(request.getQrUuid());
        return ResponseEntity.ok(qrCodeService.processQrCode(qrUuid));
    }
}