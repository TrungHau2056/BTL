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
        if (borrowService.findByUserAndDocument(user, document) != null) {
            alert.setContentText("Document were borrowed before! Thank you for using our library.");
            alert.show();
        } else {
            borrowService.borrowDocument(user, document);
            userSearchBookController.setUserInfo();

            alert.setContentText("Document borrowed successfully! Thank you for using our library.");
            alert.show();
        }
        // remember to set borrowedDate
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
            thumbnail.setImage(new Image("data:image/jpeg;base64,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"));
            // remember to change imagelink
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
