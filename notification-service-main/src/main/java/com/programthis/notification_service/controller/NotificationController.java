package com.programthis.notification_service.controller; // O tu paquete real

import com.programthis.notification_service.entity.NotificationLog;
import com.programthis.notification_service.service.EmailNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications") 
public class NotificationController {

    @Autowired
    private EmailNotificationService emailNotificationService;

  
    @PostMapping("/email")
    public ResponseEntity<String> sendEmail(@RequestBody EmailNotificationService.NotificationRequest notificationRequest) {
        try {
            emailNotificationService.sendEmailNotification(notificationRequest);
            return ResponseEntity.ok("Notification request processed.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error processing notification: " + e.getMessage());
        }
    }

    @GetMapping("/logs/receiver/{email}") 
    public ResponseEntity<List<NotificationLog>> getLogsByEmail(@PathVariable String email) {
        List<NotificationLog> logs = emailNotificationService.getLogsByReceiver(email); 
        if (logs.isEmpty()) {
            return ResponseEntity.notFound().build(); 
        }
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/logs/type/{type}") 
    public ResponseEntity<List<NotificationLog>> getLogsByType(@PathVariable String type) {
        List<NotificationLog> logs = emailNotificationService.getLogsByType(type); 
        if (logs.isEmpty()) {
            return ResponseEntity.notFound().build(); 
        }
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/logs") 
    public ResponseEntity<List<NotificationLog>> getAllLogs() {
        List<NotificationLog> logs = emailNotificationService.getAllLogs();
        return ResponseEntity.ok(logs);
    }
   @PutMapping("/logs/{id}")
public ResponseEntity<String> updateNotification(@PathVariable Long id, @RequestBody NotificationLog updatedLog) {
    boolean updated = emailNotificationService.updateNotificationLog(id, updatedLog);
    if (updated) {
        return ResponseEntity.ok("Notification updated successfully.");
    } else {
        return ResponseEntity.notFound().build();
    }
}

@DeleteMapping("/logs/{id}")
public ResponseEntity<String> deleteNotification(@PathVariable Long id) {
    boolean deleted = emailNotificationService.deleteNotificationLog(id);
    if (deleted) {
        return ResponseEntity.ok("Notification deleted successfully.");
    } else {
        return ResponseEntity.notFound().build();
    }
}

}