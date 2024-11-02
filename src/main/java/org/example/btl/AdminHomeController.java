package org.example.btl;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AdminHomeController {
    @FXML
    private HBox hbox;

    @FXML
    private Button SearchButton;

    @FXML
    public void initialize() {
        SearchButton.setOnAction(event -> {
            AdminSearchBook adminSearchBook = new AdminSearchBook();
            try {
                adminSearchBook.start((Stage)((Node) event.getSource()).getScene().getWindow());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
