package org.example.btl.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminSearchBookController {
    @FXML
    private ChoiceBox<String> TypeBook = new ChoiceBox<>();

    @FXML
    public void initialize() {
        TypeBook.getItems().addAll("TruyenCuoi", "TruyenNguNgon", "TruyenMa");
        TypeBook.setValue("The loai");
        TypeBook.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Selected item: " + newValue);
        });
    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToAdminSearchBook(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/btl/view/AdminSearchBook.fxml"));
        root = fxmlLoader.load();

        Scene scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}
