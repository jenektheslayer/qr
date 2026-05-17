package com.example.clubQR.controller;

import com.example.clubQR.entity.Participant;
import com.example.clubQR.service.QrCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/participants")
public class ParticipantController {

    private final QrCodeService qrCodeService;

    public ParticipantController(QrCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }
    @GetMapping
    public ResponseEntity<?> getAllParticipants() {

        return ResponseEntity.ok(qrCodeService.getAllParticipants());
    }

    @PostMapping
    public ResponseEntity<?> addParticipant(@RequestBody Map<String, String> request) {
        String firstName = request.get("firstName");
        String lastName = request.get("lastName");
        String middleName = request.get("middleName");

        return ResponseEntity.ok(qrCodeService.addParticipant(firstName, lastName, middleName));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateParticipant(
            @PathVariable Long id,
            @RequestBody Map<String, String> request
    ) {
        String firstName = request.get("firstName");
        String lastName = request.get("lastName");
        String middleName = request.get("middleName");

        return ResponseEntity.ok(qrCodeService.updateParticipant(id, firstName, lastName, middleName));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteParticipant(@PathVariable Long id) {
        qrCodeService.deleteParticipant(id);

        return ResponseEntity.ok(Map.of("Message", "Участник удален"));
    }

}
