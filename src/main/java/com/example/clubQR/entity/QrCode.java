package com.example.clubQR.entity;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "qr_codes")
public class QrCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "participant_id", nullable = false)
    private Participant participant;

    @Column(name = "qr_uuid", nullable = false, unique = true)
    private UUID qrUuid;

    public QrCode() {}

    public QrCode(Participant participant, UUID qrUuid) {
        this.participant = participant;
        this.qrUuid = qrUuid;
    }

    public Long getId() { return id;}
    public void setId(Long id) {this.id = id; }

    public Participant getParticipant() { return participant; }
    public void setParticipant(Participant participant) {this.participant = participant; }

    public UUID getQrUuid() { return qrUuid; }
    public void setQrUuid(UUID qrUuid) { this.qrUuid = qrUuid; }


}
