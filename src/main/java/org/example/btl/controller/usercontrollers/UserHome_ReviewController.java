package org.example.btl.controller.usercontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


public class UserHome_ReviewController extends UserBaseController {
    @FXML
    private Label userinfo;

    /**
     * set user info.
     */

    @Override
    public void setUserInfo() {
        userinfo.setText(user.getName());
        byte[] avatarData = user.getAvatar();
        if (avatarData != null) {
            InputStream inputStream = new ByteArrayInputStream(avatarData);
            avatar.setImage(new Image(inputStream));
        }
    }

    public void switchToContact(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/userview/userHome_Contact-view.fxml");
    }

    public void switchToHome(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/userview/userHome-view.fxml");
    }
}
