package com.example.clubQR.controller;

import com.example.clubQR.dto.DeleteResponse;
import com.example.clubQR.dto.ParticipantRequest;
import com.example.clubQR.dto.ParticipantResponse;
import com.example.clubQR.entity.Participant;
import com.example.clubQR.service.QrCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/participants")
public class ParticipantController {

    private final QrCodeService qrCodeService;

    public ParticipantController(QrCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }
    @GetMapping
    public ResponseEntity<List<ParticipantResponse>> getAllParticipants() {
        return ResponseEntity.ok(qrCodeService.getAllParticipants());
    }

    @PostMapping
    public ResponseEntity<ParticipantResponse> addParticipant(@RequestBody ParticipantRequest request) {
        return ResponseEntity.ok(
                qrCodeService.addParticipant(request.getFirstName(), request.getLastName(), request.getMiddleName())
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParticipantResponse> updateParticipant(
            @PathVariable Long id,
            @RequestBody ParticipantRequest request
    ) {
        return ResponseEntity.ok(
                qrCodeService.updateParticipant(
                        id, request.getFirstName(), request.getLastName(), request.getMiddleName())
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResponse> deleteParticipant(@PathVariable Long id) {
        qrCodeService.deleteParticipant(id);
        return ResponseEntity.ok(new DeleteResponse("Участник удален"));
    }

}
