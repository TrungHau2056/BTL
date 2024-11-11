package org.example.btl.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.btl.model.Author;
import org.example.btl.model.Document;

import java.io.IOException;
import java.util.Set;

public class AdminSearchBookController extends AdminBaseController {
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
    public void setAdminInfo() {

    }

    @FXML
    public void initialize() {
        // Choice Box
        TypeBook.getItems().addAll("Title", "Author", "Genre", "Id", "ISBN code");
        TypeBook.setValue("Title");
        TypeBook.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Selected item: " + newValue);
        });

        // Table view
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        authorsCol.setCellValueFactory(new PropertyValueFactory<>("authors"));

        ObservableList<Document> observableList = FXCollections.observableArrayList(
                new Document(1, "Sach 1", "Mieu ta ve sach1"),
                new Document(2, "Sach 2", "Mieu ta sach 2"),
                new Document(3, "Sach 3", "Mieu ta sach 3")
        );

        tableView.setItems(observableList);
    }

    @FXML
    public void switchToAddMemberScene(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/addMember.fxml");
    }

    @FXML
    public void switchToAdminHome(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/adminHome.fxml");
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
