package com.programthis.notification_service.repository;

import com.programthis.notification_service.entity.NotificationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationLogRepository extends JpaRepository<NotificationLog, Long> {
    List<NotificationLog> findByReceiver(String receiver);
    List<NotificationLog> findByType(String type);
    List<NotificationLog> findByTypeAndStatus(String type, String status);
    
}
