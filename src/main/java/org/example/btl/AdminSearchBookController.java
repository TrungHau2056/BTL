package org.example.btl;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class AdminSearchBookController {
    @FXML
    private ChoiceBox<String> Type = new ChoiceBox<>();

    @FXML
    public void initialize() {
        Type.getItems().addAll("TruyenCuoi", "TruyenNguNgon", "TruyenMa");
        Type.setValue("The loai");
        Type.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Selected item: " + newValue);
        });
    }

}
