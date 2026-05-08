package com.example.clubQR.service;


import com.example.clubQR.entity.Participant;
import com.example.clubQR.entity.QrCode;
import com.example.clubQR.exception.QrCodeNotFoundException;
import com.example.clubQR.repository.ParticipantRepository;
import com.example.clubQR.repository.QrCodeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public String processQrCode(UUID qrUuid) {
        Optional<QrCode> optionalQrCode = qrCodeRepository.findByQrUuid(qrUuid);

        QrCode qrCode = optionalQrCode.orElseThrow(() ->
                new QrCodeNotFoundException("Недействительный QR - код: " + qrUuid) // или здесь кастомный IOException??(спросить)
        );

        Participant participant = qrCode.getParticipant();
        qrCode.setQrUuid(UUID.randomUUID());
        qrCodeRepository.save(qrCode);

        return participant.getFullName();
    }

    public List<Participant> getAllParticipants() {
        return participantRepository.findAll();
    }

    @Transactional
    public Participant addParticipant(String firstName, String lastName, String middleName) {
        Participant participant = new Participant(firstName, lastName, middleName);
        participantRepository.save(participant);

        QrCode qrCode = new QrCode(participant, UUID.randomUUID());
        qrCodeRepository.save(qrCode);

        return participant;
    }

    @Transactional
    public Participant updateParticipant(Long id, String firstName, String lastName, String middleName) {
        Participant participant = participantRepository
                .findById(id).orElseThrow(() -> new QrCodeNotFoundException("Участник не найден" + id));
        participant.setFirstName(firstName);
        participant.setLastName(lastName);
        participant.setMiddleName(middleName);
        participantRepository.save(participant);

        return participant;
    }

    @Transactional
    public void deleteParticipant(Long id) {
        participantRepository.deleteById(id);
    }
}
