package org.example.btl.controller.admincontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddByISBNController extends AdminBaseController {
    @FXML
    TextField isbnText;
    @FXML
    TextField titleText;
    @FXML
    TextField quantityText;
    @FXML
    TextField genreText;
    @FXML
    TextArea descriptionText;
    @FXML
    TextField authorText;
    @FXML
    TextField publisherText;

    @Override
    public void setAdminInfo() {

    }

    public void handleCheck(ActionEvent event) {
        String isbn = isbnText.getText();
    }
}
