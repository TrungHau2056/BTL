package org.example.btl.service;

import org.example.btl.dao.NotificationDAO;
import org.example.btl.model.Notification;
import org.example.btl.model.User;

public class NotificationService {
    private NotificationDAO notificationDAO = new NotificationDAO();

    public void addNotification(User user, String message) {
        notificationDAO.saveWithUser(new Notification(message), user);
    }
}
