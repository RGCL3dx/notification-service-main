package com.programthis.notification_service.service;

import com.programthis.notification_service.entity.NotificationLog;
import com.programthis.notification_service.repository.NotificationLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmailNotificationServicetest {

    @InjectMocks
    private EmailNotificationService service;

    @Mock
    private NotificationLogRepository repository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetLogsByReceiver() {
        String receiver = "usuario@gmail.com";

        NotificationLog log = new NotificationLog();
        log.setReceiver(receiver);
        log.setType("EMAIL");
        log.setMessage("Mensaje");
        log.setStatus("Enviado");
        log.setSubject("Asunto");
        log.setTimestamp(LocalDateTime.now());

        when(repository.findByReceiver(receiver)).thenReturn(List.of(log));

        List<NotificationLog> result = service.getLogsByReceiver(receiver);

        assertEquals(1, result.size());
        assertEquals(receiver, result.get(0).getReceiver());
        verify(repository).findByReceiver(receiver);
    }

    @Test
    public void testGetLogsByType() {
        String type = "EMAIL";

        NotificationLog log = new NotificationLog();
        log.setReceiver("usuario@gmail.com");
        log.setType(type);
        log.setMessage("Mensaje");
        log.setStatus("Enviado");
        log.setSubject("Asunto");
        log.setTimestamp(LocalDateTime.now());


        when(repository.findByType(type)).thenReturn(List.of(log));

        List<NotificationLog> result = service.getLogsByType(type);

        assertEquals(1, result.size());
        assertEquals(type, result.get(0).getType());
        verify(repository).findByType(type);
    }

    @Test
    public void testGetAllLogs() {
        NotificationLog log = new NotificationLog();
        log.setReceiver("usuario@gmail.com");

        List<NotificationLog> logs = List.of(log);
        when(repository.findAll()).thenReturn(logs);

        List<NotificationLog> result = service.getAllLogs();

        assertEquals(1, result.size());
        verify(repository).findAll();
    }

    @Test
    public void testUpdateNotificationLog() {
        Long id = 1L;
        NotificationLog updatedLog = new NotificationLog();
        updatedLog.setReceiver("usuario@gmail.com");
        updatedLog.setMessage("Nuevo mensaje");
        updatedLog.setType("EMAIL");
        updatedLog.setTimestamp(LocalDateTime.now());
        updatedLog.setSubject("Nuevo asunto");
        updatedLog.setStatus("Leído");

        NotificationLog existingLog = new NotificationLog();
        existingLog.setLogId(id);

        when(repository.findById(id)).thenReturn(Optional.of(existingLog));

        boolean result = service.updateNotificationLog(id, updatedLog);

        assertTrue(result);
        verify(repository).save(existingLog);
    }

    @Test
    public void testDeleteNotificationLog() {
        Long id = 1L;
        when(repository.existsById(id)).thenReturn(true);

        boolean result = service.deleteNotificationLog(id);

        assertTrue(result);
        verify(repository).deleteById(id);
    }
}