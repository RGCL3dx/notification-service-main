package com.programthis.notification_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    private String receiver; // Ej: email, userId o systema de grupo administrativo
    private String type; // tipo de notificacion
    private String subject;
    @jakarta.persistence.Column(length = 1000) // Para mensajes más largos
    private String message;
    private String status; // estatus
    private LocalDateTime timestamp;
}