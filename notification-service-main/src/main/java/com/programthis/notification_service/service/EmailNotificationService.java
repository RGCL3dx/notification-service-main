package com.programthis.notification_service.service;

import com.programthis.notification_service.entity.NotificationLog;
import com.programthis.notification_service.repository.NotificationLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmailNotificationService { 

    @Autowired
    private NotificationLogRepository notificationLogRepository;

    public static record NotificationRequest(String receiverEmail, String subject, String messageBody, String type) {}
    
    public void sendEmailNotification(NotificationRequest request) {
        NotificationLog log = new NotificationLog();
        log.setReceiver(request.receiverEmail());
        log.setType(request.type());
        log.setSubject(request.subject());
        log.setMessage(request.messageBody());
        log.setTimestamp(LocalDateTime.now());

        try {

            System.out.println("Simulando Email: " + request.receiverEmail());
            System.out.println("Sujeto: " + request.subject());
            System.out.println("cuerpo de texto: " + request.messageBody());

            log.setStatus("Enviado");
        } catch (Exception e) {

            System.err.println("error de envio : " + e.getMessage());
            log.setStatus("Error");
        }
        notificationLogRepository.save(log);
    }
    public List<NotificationLog> getLogsByReceiver(String email) {
    return notificationLogRepository.findByReceiver(email);
}

public List<NotificationLog> getLogsByType(String type) {
    return notificationLogRepository.findByType(type); // Llama al nuevo método del repositorio
}

public List<NotificationLog> getAllLogs() {
    return notificationLogRepository.findAll();
}


public boolean updateNotificationLog(Long id, NotificationLog updatedLog) {
    return notificationLogRepository.findById(id).map(existingLog -> {
        existingLog.setReceiver(updatedLog.getReceiver());
        existingLog.setMessage(updatedLog.getMessage());
        existingLog.setType(updatedLog.getType());
        existingLog.setTimestamp(updatedLog.getTimestamp());
        notificationLogRepository.save(existingLog);
        return true;
    }).orElse(false);
}

public boolean deleteNotificationLog(Long id) {
    if (notificationLogRepository.existsById(id)) {
        notificationLogRepository.deleteById(id);
        return true;
    }
    return false;
}


}


