package org.example.btl.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminDeleteBookController extends AdminBaseController {
    @Override
    public void setAdminInfo() {

    }

    @FXML
    public void switchToAdminHome(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/adminHome.fxml");
    }

    @FXML
    public void switchToAdminSearchBook(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/adminSearchBook.fxml");
    }

    @FXML
    public void switchToAddBookScene(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/adminAddBook.fxml");
    }

    @FXML
    public void switchToAddMemberScene(ActionEvent event) throws  IOException {
        switchScene(event, "/org/example/btl/view/addMember.fxml");
    }
}
