package org.example.btl.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.example.btl.controller.usercontrollers.UserNotificationController;
import org.example.btl.model.Notification;
import org.example.btl.model.User;
import org.example.btl.service.NotificationService;

public class NotificationController {
    private User user;
    private Notification notification;

    private NotificationService notificationService = new NotificationService();

    private UserNotificationController userNotificationController;

    @FXML
    private Label nameLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private TextArea messageText;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public void setUserNotificationController(UserNotificationController userNotificationController) {
        this.userNotificationController = userNotificationController;
    }

    public void setNotificationInfo() {
        if (!notification.isRead()) {
            user = notificationService.switchNotificationStatus(notification);
            userNotificationController.setUser(user);
            userNotificationController.setUserInfo();
        }

        nameLabel.setText(user.getName() );
        titleLabel.setText(notification.getTitle());
        messageText.setText(notification.getMessage());
        timeLabel.setText(notificationService.getDuration(notification.getCreatedAt()));
    }

    public void handleRemove(ActionEvent event) {
        user = notificationService.deleteNotification(notification);
        userNotificationController.setUser(user);
        userNotificationController.setUserInfo();

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void handleUnread(ActionEvent event) {
        user = notificationService.switchNotificationStatus(notification);
        userNotificationController.setUser(user);
        userNotificationController.setUserInfo();

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
}
