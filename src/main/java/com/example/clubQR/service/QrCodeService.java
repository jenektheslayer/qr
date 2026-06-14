package com.example.clubQR.service;


import com.example.clubQR.dto.ParticipantRequest;
import com.example.clubQR.dto.ParticipantResponse;
import com.example.clubQR.dto.QrCheckResponse;
import com.example.clubQR.entity.Participant;
import com.example.clubQR.entity.QrCode;
import com.example.clubQR.exception.ParticipantNotFoundException;
import com.example.clubQR.exception.QrCodeNotFoundException;
import com.example.clubQR.mapper.ParticipantMapper;
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
                new QrCodeNotFoundException("Недействительный QR - код: " + qrUuid)
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
            responses.add(ParticipantMapper.toDto(p));
        }
        return responses;
    }

    @Transactional
    public ParticipantResponse addParticipant(ParticipantRequest request) {
        Participant participant = new Participant(request.getFirstName(), request.getLastName(),request.getMiddleName());
        participant = participantRepository.save(participant);

        QrCode qrCode = new QrCode(participant, UUID.randomUUID());
        qrCodeRepository.save(qrCode);

        return ParticipantMapper.toDto(participant);
    }

    @Transactional
    public ParticipantResponse updateParticipant(Long id, ParticipantRequest request) {
        Participant participant = participantRepository
                .findById(id).orElseThrow(() -> new ParticipantNotFoundException("Участник не найден " + id));
        participant.setFirstName(request.getFirstName());
        participant.setLastName(request.getLastName());
        participant.setMiddleName(request.getMiddleName());
        participant = participantRepository.save(participant);

        return ParticipantMapper.toDto(participant);
    }

    @Transactional
    public void deleteParticipant(Long id) {
        Participant participant = participantRepository.findById(id)
                .orElseThrow(() -> new ParticipantNotFoundException("Участник не найден: " + id));
        participantRepository.delete(participant);
    }
}
