package com.example.clubQR.service;


import com.example.clubQR.dto.ParticipantResponse;
import com.example.clubQR.dto.QrCheckResponse;
import com.example.clubQR.entity.Participant;
import com.example.clubQR.entity.QrCode;
import com.example.clubQR.exception.QrCodeNotFoundException;
import com.example.clubQR.repository.ParticipantRepository;
import com.example.clubQR.repository.QrCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class QrCodeService {

    private final QrCodeRepository qrCodeRepository;
    private final ParticipantRepository participantRepository;

    @Autowired
    public QrCodeService(QrCodeRepository qrCodeRepository, ParticipantRepository participantRepository) {
        this.qrCodeRepository = qrCodeRepository;
        this.participantRepository = participantRepository;
    }

    @Transactional
    public QrCheckResponse processQrCode(UUID qrUuid) {
        Optional<QrCode> optionalQrCode = qrCodeRepository.findByQrUuid(qrUuid);

        QrCode qrCode = optionalQrCode.orElseThrow(() ->
                new QrCodeNotFoundException("Недействительный QR - код: " + qrUuid) // или здесь кастомный IOException??(спросить)
        );

        Participant participant = qrCode.getParticipant();
        qrCode.setQrUuid(UUID.randomUUID());
        qrCodeRepository.save(qrCode);

        return new QrCheckResponse(participant.getFullName());
    }

    public List<ParticipantResponse> getAllParticipants() {
        List<Participant> participants = participantRepository.findAll();
        List<ParticipantResponse> responses = new ArrayList<>();

        for (Participant p : participants) {
            responses.add(new ParticipantResponse(
                p.getId(),
                p.getFirstName(),
                p.getLastName(),
                p.getMiddleName(),
                p.getFullName()
            ));
        }
        return responses;
    }

    @Transactional
    public ParticipantResponse addParticipant(String firstName, String lastName, String middleName) {
        Participant participant = new Participant(firstName, lastName, middleName);
        participant = participantRepository.save(participant);

        QrCode qrCode = new QrCode(participant, UUID.randomUUID());
        qrCodeRepository.save(qrCode);

        return new ParticipantResponse(
                participant.getId(),
                participant.getFirstName(),
                participant.getLastName(),
                participant.getMiddleName(),
                participant.getFullName()
        );
    }

    @Transactional
    public ParticipantResponse updateParticipant(Long id, String firstName, String lastName, String middleName) {
        Participant participant = participantRepository
                .findById(id).orElseThrow(() -> new QrCodeNotFoundException("Участник не найден" + id));
        participant.setFirstName(firstName);
        participant.setLastName(lastName);
        participant.setMiddleName(middleName);
        participant = participantRepository.save(participant);

        return new ParticipantResponse(
                participant.getId(),
                participant.getFirstName(),
                participant.getLastName(),
                participant.getMiddleName(),
                participant.getFullName()
        );
    }

    @Transactional
    public void deleteParticipant(Long id) {
        participantRepository.deleteById(id);
    }
}
