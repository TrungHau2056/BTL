package org.example.btl.service;

import org.example.btl.dao.NotificationDAO;
import org.example.btl.model.Notification;
import org.example.btl.model.User;

import java.time.Duration;
import java.time.LocalDateTime;

public class NotificationService {
    private NotificationDAO notificationDAO = new NotificationDAO();

    public User addNotification(User user, String title, String message) {
        return notificationDAO.saveWithUser(new Notification(title, message), user);
    }

    public User deleteNotification(Notification notification) {
        return notificationDAO.deleteNotification(notification);
    }

    public String getDuration(LocalDateTime createdAt) {
        long seconds = Duration.between(createdAt, LocalDateTime.now()).getSeconds();
        if (seconds < 60) {
            return seconds + (seconds == 1 ? " second ago" : " seconds ago");
        }

        long minutes = seconds / 60;
        if (minutes < 60) {
            return minutes + (minutes == 1 ? " minute ago" : " minutes ago");
        }

        long hours = minutes / 60;
        if (hours < 24) {
            return hours + (hours == 1 ? " hour ago" : " hours ago");
        }

        long days = hours / 24;
        if (days < 31) {
            return days + (days == 1 ? " day ago" : " days ago");
        }

        long months = days / 31;
        return months + (months == 1 ? " month ago" : " months ago");
    }
}
