package org.example.btl.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.btl.controller.usercontrollers.UserSearchBookController;
import org.example.btl.model.Author;
import org.example.btl.model.Document;
import org.example.btl.model.Genre;
import org.example.btl.service.BorrowService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BookInfoController implements Initializable {
    private Document document;

    private BorrowService borrowService = new BorrowService();

    @FXML
    private ImageView thumbnail;
    @FXML
    private Label titleText;
    @FXML
    private Label authorText;
    @FXML
    private Label genreText;
    @FXML
    private Label publisherText;
    @FXML
    private Label languageText;
    @FXML
    private Label quantityText;
    @FXML
    private Label descriptionText;

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    private UserSearchBookController userSearch;

    public void setUserSearch(UserSearchBookController userSearch) {
        this.userSearch = userSearch;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String authors = "";
        String genres = "";
        for (Author author : document.getAuthors()) {
            authors += author.getName();
            authors += ", ";
        }

        for (Genre genre : document.getGenres()) {
            genres += genre.getName();
            genres += ", ";
        }

        String imageLink = document.getImageLink();

        if (imageLink != null) {
            thumbnail.setImage(new Image(document.getImageLink()));
        } else {

        }
        titleText.setText(document.getTitle());
        authorText.setText(authors);
        genreText.setText(genres);
        publisherText.setText(document.getPublisher().getName());
        quantityText.setText(String.valueOf(document.getQuantity()));
        descriptionText.setText(document.getDescription());
    }

    public void handleBorrow(ActionEvent event) {

    }

    @FXML
    public void handleReturnButton(ActionEvent event) throws IOException {
        userSearch.handleBackButton(event);
    }

    public void setBookInfo() {
        String authors = "";
        String genres = "";
        for (Author author : document.getAuthors()) {
            authors += author.getName();
            authors += ", ";
        }

        for (Genre genre : document.getGenres()) {
            genres += genre.getName();
            genres += ", ";
        }

        String imageLink = document.getImageLink();

        if (imageLink != "") {
            thumbnail.setImage(new Image(document.getImageLink()));
        } else {

        }
        titleText.setText(document.getTitle());
        authorText.setText(authors);
        genreText.setText(genres);
        publisherText.setText(document.getPublisher().getName());
        quantityText.setText(String.valueOf(document.getQuantity()));
        descriptionText.setText(document.getDescription());
    }
}
