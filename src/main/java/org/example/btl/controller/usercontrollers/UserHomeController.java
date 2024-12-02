package org.example.btl.controller.usercontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import org.example.btl.model.Notification;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class UserHomeController extends UserBaseController {
    @FXML
    private Label userinfo;
    @FXML
    private Label username;
    @FXML
    private Label userid;
    @FXML
    private Label useremail;
    @FXML
    private Label notificationTitle;
    @FXML
    private Label notification;

    /**
     * set user for scene.
     */
    @Override
    public void setUserInfo() {
        userinfo.setText(user.getName());
        username.setText("About " + user.getName());
        userid.setText("ID: " + user.getId());
        useremail.setText("Email: " + user.getEmail());

        List<Notification> notifications = userService.getNotifications(user);
        notificationTitle.setText(notifications.getLast().getTitle());
        notification.setText(notifications.getLast().getMessage());

        byte[] avatarData = user.getAvatar();
        if (avatarData != null) {
            InputStream inputStream = new ByteArrayInputStream(avatarData);
            avatar.setImage(new Image(inputStream));
        }
    }

    /**
     * switch scene in userHome.
     *
     * @param event the action event triggered by the user.
     * @throws IOException if the FXML file cannot be loaded.
     */
    public void switchToContact(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/userview/userHome_Contact-view.fxml");
    }

    public void switchToReview(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/userview/userHome_Review-view.fxml");
    }
}
