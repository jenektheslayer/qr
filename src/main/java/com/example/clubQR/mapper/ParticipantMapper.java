package com.example.clubQR.mapper;

import com.example.clubQR.dto.ParticipantResponse;
import com.example.clubQR.entity.Participant;

public class ParticipantMapper {

    public static ParticipantResponse toDto(Participant participant) {
        return new ParticipantResponse(
                participant.getId(),
                participant.getFirstName(),
                participant.getLastName(),
                participant.getMiddleName(),
                participant.getFullName()
        );
    }
}


