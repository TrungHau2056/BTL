package org.example.btl.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.btl.controller.usercontrollers.UserSearchBookController;
import org.example.btl.dao.BorrowDAO;
import org.example.btl.model.Author;
import org.example.btl.model.Document;
import org.example.btl.model.Genre;
import org.example.btl.model.User;
import org.example.btl.service.BorrowService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BookInfoController implements Initializable {
    private Document document;

    private BorrowService borrowService = new BorrowService();
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);

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
    private Label quantityText;
    @FXML
    private Label descriptionText;

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    private User user;

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    private UserSearchBookController userSearchBookController;

    public void setUserSearchBookController(UserSearchBookController userSearchBookController) {
        this.userSearchBookController = userSearchBookController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//
    }


    public void handleBorrow(ActionEvent event) {
        String validateMess = borrowService.validateBorrow(user, document);
        if (validateMess != null) {
            alert.setContentText(validateMess);
            alert.show();
        } else {
            borrowService.borrowDocument(user, document);
            userSearchBookController.setUserInfo();
            userSearchBookController.refresh();

            alert.setContentText("Document borrowed successfully! Thank you for using our library.");
            alert.show();
        }
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

        if (imageLink != null) {
            thumbnail.setImage(new Image(document.getImageLink()));
        } else {

        }
        titleText.setText(document.getTitle());
        authorText.setText(authors);
        genreText.setText(genres);
        if (document.getPublisher() != null) {
            publisherText.setText(document.getPublisher().getName());
        }
        quantityText.setText(String.valueOf(document.getQuantity()));
        descriptionText.setText(document.getDescription());
    }
}
