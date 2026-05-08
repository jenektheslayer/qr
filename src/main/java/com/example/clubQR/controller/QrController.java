package com.example.clubQR.controller;


import com.example.clubQR.service.QrCodeService;
import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class QrController {

    private final QrCodeService qrCodeService;
    private final HandlerMapping resourceHandlerMapping;

    public QrController(QrCodeService qrCodeService, @Nullable HandlerMapping resourceHandlerMapping) {
        this.qrCodeService = qrCodeService;
        this.resourceHandlerMapping = resourceHandlerMapping;
    }
    @PostMapping("qr/check")
    public ResponseEntity<Map<String, String>> checkQrCode (@RequestBody Map<String, String> request) {
        String uuidString = request.get("qrUuid");
        UUID qrUuid = UUID.fromString(uuidString);
        String fullName = qrCodeService.processQrCode(qrUuid);
        return ResponseEntity.ok(Map.of("fullName", fullName));
    }

    @GetMapping("/participants")
    public ResponseEntity<?> getAllParticipants() {

        return ResponseEntity.ok(qrCodeService.getAllParticipants());
    }

    @PostMapping("/participants")
    public ResponseEntity<?> addParticipant(@RequestBody Map<String, String> request) {
        String firstName = request.get("firstName");
        String lastName = request.get("lastName");
        String middleName = request.get("middleName");

        return ResponseEntity.ok(qrCodeService.addParticipant(firstName, lastName, middleName));
    }

    @PutMapping("/participants/{id}")
    public ResponseEntity<?> updateParticipant(
            @PathVariable Long id,
            @RequestBody Map<String, String> request
    ) {
        String firstName = request.get("firstName");
        String lastName = request.get("lastName");
        String middleName = request.get("middleName");

        return ResponseEntity.ok(qrCodeService.updateParticipant(id, firstName, lastName, middleName));
    }

    @DeleteMapping("/participant/{id}")
    public ResponseEntity<?> deleteParticipant(@PathVariable Long id) {
        qrCodeService.deleteParticipant(id);

        return ResponseEntity.ok(Map.of("Message", "Участник удален"));
    }
}

