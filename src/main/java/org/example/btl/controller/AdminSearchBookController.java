package org.example.btl.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.btl.model.Author;
import org.example.btl.model.Document;

import javax.print.Doc;
import java.io.IOException;
import java.util.Set;

public class AdminSearchBookController {
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
    public void switchToAddMemberScene(ActionEvent event) throws  IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/btl/view/addMember.fxml"));
        root = fxmlLoader.load();
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

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
}
