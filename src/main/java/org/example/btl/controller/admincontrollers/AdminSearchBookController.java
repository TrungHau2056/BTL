package org.example.btl.controller.admincontrollers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.btl.model.Author;
import org.example.btl.model.Document;
import org.example.btl.model.Genre;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

public class AdminSearchBookController extends AdminBaseController {
    @FXML
    private TextField titleSearchText;

    @FXML
    private ChoiceBox<String> TypeBook;
    @FXML
    private TableView<Document> tableView;
    @FXML
    private TableColumn<Document, Integer> idCol;
    @FXML
    private TableColumn<Document, String> titleCol;
    @FXML
    private TableColumn<Document, String> authorsCol;
    @FXML
    private TableColumn<Document, String> genresCol;
    @FXML
    private TableColumn<Document, String> descriptionCol;
    @FXML
    private TableColumn<Document, Integer> quantityCol;

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
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        authorsCol.setCellValueFactory(data -> {
            Set<Author> authors = data.getValue().getAuthors();
            String authorsString = authors.stream()
                    .map(Author::getName)
                    .collect(Collectors.joining(", "));
            return new SimpleStringProperty(authorsString);
        });

        genresCol.setCellValueFactory(data -> {
            Set<Genre> genres = data.getValue().getGenres();
            String genresString = genres.stream()
                    .map(Genre::getName)
                    .collect(Collectors.joining(", "));
            return new SimpleStringProperty(genresString);
        });

        ObservableList<Document> observableList = FXCollections.observableArrayList();
        tableView.setItems(observableList);
    }

    public void handleAdminSearch(ActionEvent event) {

    }
}
