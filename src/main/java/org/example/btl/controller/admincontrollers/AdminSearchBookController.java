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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AdminSearchBookController extends AdminBaseController {
    @FXML
    private TextField searchText;

    @FXML
    private ChoiceBox<String> criteria;
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
    private TableColumn<Document, String> publisherCol;
    @FXML
    private TableColumn<Document, String> descriptionCol;
    @FXML
    private TableColumn<Document, Integer> quantityCol;

    private ObservableList<Document> documentObservableList;

    @Override
    public void setAdminInfo() {
        ObservableList<Document> observableList = FXCollections.observableArrayList(documentService.findAll());
        tableView.setItems(observableList);
    }

    @FXML
    public void initialize() {
        // Choice Box
        criteria.getItems().addAll("Title", "Author", "Genre", "Publisher");
        criteria.setValue("Title");

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

        publisherCol.setCellValueFactory(data -> {
            return new SimpleStringProperty(
                    data.getValue().getPublisher() != null ? data.getValue().getPublisher().getName() : "Not available");
        });
    }

    public void handleAdminSearch(ActionEvent event) {
        String keyword = searchText.getText();
        String criterion = criteria.getValue();

        String validateMessage = documentService.validateSearchByKeyword(keyword);
        if (validateMessage != null) {
            alertErr.setContentText(keyword);
            alertErr.show();
        } else {
            List<Document> documents = null;
            switch (criterion) {
                case "Title":
                    documents = documentService.searchByTitleKeyword(keyword);
                    break;
                case "Author":
                    documents = documentService.searchByAuthorKeyword(keyword);
                    break;
                case "Genre":
                    documents = documentService.searchByGenreKeyword(keyword);
                    break;
                case "Publisher":
                    documents = documentService.searchByPublisherKeyword(keyword);
                    break;
            }

            if(documents.isEmpty()) {
                alertErr.setContentText("No search results match the keyword.");
                alertErr.show();
            } else {
                documentObservableList = FXCollections.observableArrayList(documents);
                tableView.setItems(documentObservableList);
            }
        }
    }
}
