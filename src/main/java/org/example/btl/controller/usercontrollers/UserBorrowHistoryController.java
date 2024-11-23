package org.example.btl.controller.usercontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;

import java.net.URL;
import java.util.ResourceBundle;

public class UserBorrowHistoryController extends UserBaseController implements Initializable {
    @FXML
    private ToggleButton historyButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        historyButton.setSelected(true);

    }

    @Override
    public void setUserInfo() {

    }

    public void switchToSearchScene(ActionEvent event) {

    }
}
