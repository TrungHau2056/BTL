package org.example.btl.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class AdminHomeController extends AdminBaseController {
    @Override
    public void setAdminInfo() {

    }

    @FXML
    public void switchToAdminSearchBook(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/adminSearchBook.fxml");
    }

    @FXML
    public void switchToAddMemberScene(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/addMember.fxml");
    }

    @FXML
    public void switchToAddBookScene(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/adminAddBook.fxml");
    }

    @FXML
    public void switchToDeleteBookScene(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/adminDeleteBook.fxml");
    }
}
