package org.example.btl.controller.usercontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import org.example.btl.model.Document;

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

    }

    public void switchToSearchScene(ActionEvent event) {

    }
}
