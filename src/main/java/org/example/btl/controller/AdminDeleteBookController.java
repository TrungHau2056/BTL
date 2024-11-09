package org.example.btl.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminDeleteBookController {

    private Scene scene;
    private Stage stage;
    private Parent root;

    @FXML
    public void switchToAdminHome(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/btl/view/adminHome.fxml"));
        root = fxmlLoader.load();
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void switchToAdminSearchBook(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/btl/view/adminSearchBook.fxml"));
        root = fxmlLoader.load();
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void switchToAddBookScene(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/btl/view/adminAddBook.fxml"));
        root = fxmlLoader.load();
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void switchToAddMemberScene(ActionEvent event) throws  IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/btl/view/addMember.fxml"));
        root = fxmlLoader.load();
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
