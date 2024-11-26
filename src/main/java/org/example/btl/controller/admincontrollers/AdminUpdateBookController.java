package org.example.btl.controller.admincontrollers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.btl.model.Author;
import org.example.btl.model.Document;
import org.example.btl.model.Genre;

public class AdminUpdateBookController extends AdminBaseController {
    private Document document;

    @FXML
    private TextField titleText;
    @FXML
    private TextField quantityText;
    @FXML
    private ListView<String> genreList;
    @FXML
    private ListView<String> authorList;
    @FXML
    private TextArea descriptionText;
    @FXML
    private TextField publisherText;
    @FXML
    private TextField imageLinkText;

    @FXML
    private Label warnLabel;
    @FXML
    private Button updateButton;


    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    @Override
    public void setAdminInfo() {
        titleText.setText(document.getTitle());
        quantityText.setText(String.valueOf(document.getQuantity()));
        descriptionText.setText(document.getDescription());
        imageLinkText.setText(document.getImageLink());

        if (document.getPublisher() != null) {
            publisherText.setText(document.getPublisher().getName());
        }

        ObservableList<String> authorObservableList = FXCollections.observableArrayList();
        for (Author author : document.getAuthors()) {
            authorObservableList.add(author.getName());
        }
        authorList.setItems(authorObservableList);

        ObservableList<String> genreObservableList = FXCollections.observableArrayList();
        for (Genre genre : document.getGenres()) {
            genreObservableList.add(genre.getName());
        }
        genreList.setItems(genreObservableList);

        if (!document.isAddedByISBN()) {
            warnLabel.setVisible(false);
        }
    }

    public void handleUpdateTitle() {
        if (!document.isAddedByISBN()) {
            titleText.setDisable(false);
            updateButton.setDisable(false);
        }
    }

    public void handleUpdateQuantity() {
        quantityText.setDisable(false);
        updateButton.setDisable(false);
    }

    public void handleUpdateGenre() {
        if (!document.isAddedByISBN()) {
            genreList.setDisable(false);
            updateButton.setDisable(false);
        }
    }

    public void handleUpdateAuthor() {
        if (!document.isAddedByISBN()) {
            authorList.setDisable(false);
            updateButton.setDisable(false);
        }
    }

    public void handleUpdateDescription() {
        if (!document.isAddedByISBN()) {
            descriptionText.setDisable(false);
            updateButton.setDisable(false);
        }
    }

    public void handleUpdatePublisher() {
        if (!document.isAddedByISBN()) {
            publisherText.setDisable(false);
            updateButton.setDisable(false);
        }
    }

    public void handleUpdateImageLink() {
        if (!document.isAddedByISBN()) {
            imageLinkText.setDisable(false);
            updateButton.setDisable(false);
        }
    }

    public void handleUpdate() {

    }
}
