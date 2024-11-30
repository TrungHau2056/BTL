package org.example.btl.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class NotificationServiceTest {
    NotificationService notificationService = new NotificationService();

    @Test
    void getDuration_10s() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime createdAt = now.minusSeconds(10);
        assertEquals("10 seconds ago", notificationService.getDuration(createdAt));
    }

    @Test
    void getDuration_1m() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime createdAt = now.minusSeconds(80);
        assertEquals("1 minute ago", notificationService.getDuration(createdAt));
    }

    @Test
    void getDuration_1d() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime createdAt = now.minusHours(30);
        assertEquals("1 day ago", notificationService.getDuration(createdAt));
    }
}