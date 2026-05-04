package com.example.clubQR.service;


import com.example.clubQR.entity.Participant;
import com.example.clubQR.entity.QrCode;
import com.example.clubQR.repository.ParticipantRepository;
import com.example.clubQR.repository.QrCodeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class QrCodeService {

    private final QrCodeRepository qrCodeRepository;
    private final ParticipantRepository participantRepository;

    public QrCodeService(QrCodeRepository qrCodeRepository, ParticipantRepository participantRepository) {
        this.qrCodeRepository = qrCodeRepository;
        this.participantRepository = participantRepository;
    }
    // уточнить почему в конструкторе выделяется красным подеркиванием параметры (нужно ли добавить @Autowire?

    @Transactional
    public String processQrCode(UUID qrUuid) {
        Optional<QrCode> optionalQrCode = qrCodeRepository.findByQrUuid(qrUuid);

        QrCode qrCode = optionalQrCode.orElseThrow(() ->
                new RuntimeException("Недействительный QR - код: " + qrUuid) // или здесь кастомный IOException??(спросить)
        );
        //public class QrCodeNotFoundException extends RuntimeException {
        //    public QrCodeNotFoundException(String message) {
        //        super(message);
        //    }
        //} такое добавить думаю
        // throw new QrCodeNotFoundException("Недействительный QR-код: " + qrUuid);
        Participant participant = qrCode.getParticipant();
        qrCode.setQrUuid(UUID.randomUUID());
        qrCodeRepository.save(qrCode);

        return participant.getFullName();
    }




}
