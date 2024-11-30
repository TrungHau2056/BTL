package org.example.btl.controller.usercontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import org.example.btl.model.User;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class UserHome_ReviewController extends UserBaseController implements Initializable {
    @FXML
    private Label userinfo;
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void setUserInfo() {
        userinfo.setText(user.getName());
        byte[] avatarData = user.getAvatar();
        if (avatarData != null) {
            InputStream inputStream = new ByteArrayInputStream(avatarData);
            avatar.setImage(new Image(inputStream));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void switchToContact(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/userview/userHome_Contact-view.fxml");
    }

    public void switchToHome(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/userview/userHome-view.fxml");
    }
}
