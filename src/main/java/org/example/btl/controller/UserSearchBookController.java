package org.example.btl.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.example.btl.model.Author;
import org.example.btl.model.Document;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class UserSearchBookController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ChoiceBox<String> TypeBook;

    @FXML
    private TableView<Document> tableView = new TableView();
    @FXML
    private TableColumn<Document, Integer> idCol;
    @FXML
    private TableColumn<Document, String> titleCol;
    @FXML
    private TableColumn<Document, String> descriptionCol;
    @FXML
    private TableColumn<Document, Set<Author>> authorsCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    // button to switch to user home
    public void switchToUserHomeScreen(ActionEvent event) throws IOException {

    }

    // button to switch to borrowbook scene
    public void switchToUserBorrowBook(ActionEvent event) throws IOException {

    }

    // button to switch to returnbook scene
    public void switchToUserReturnBook(ActionEvent event) throws IOException {

    }

    // button to switch to returnbook scene
    public void switchToUserInfo(ActionEvent event) throws IOException {

    }
}
