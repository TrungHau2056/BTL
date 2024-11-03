package org.example.btl.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminSearchBookController {
    @FXML
    private ChoiceBox<String> TypeBook;

    @FXML
    public void initialize() {
        TypeBook.getItems().addAll("TruyenCuoi", "TruyenNguNgon", "TruyenMa");
        TypeBook.setValue("The loai");
        TypeBook.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Selected item: " + newValue);
        });
    }

}
