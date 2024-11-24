package org.example.btl.controller.usercontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import org.example.btl.model.Document;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class UserBorrowHistoryController extends UserBaseController implements Initializable {
    @FXML
    private ToggleButton historyButton;

    @FXML
    private TableView<Document> tableView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        historyButton.setSelected(true);

    }

    @Override
    public void setUserInfo() {
        nameLabel.setText(user.getName());
        byte[] avatarData = user.getAvatar();
        if (avatarData != null) {
            InputStream inputStream = new ByteArrayInputStream(avatarData);
            avatar.setImage(new Image(inputStream));
        }


    }
}
