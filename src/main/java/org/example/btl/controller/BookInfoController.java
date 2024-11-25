package org.example.btl.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.btl.controller.usercontrollers.UserReturnBookController;
import org.example.btl.controller.usercontrollers.UserSearchBookController;
import org.example.btl.dao.BorrowDAO;
import org.example.btl.model.Author;
import org.example.btl.model.Document;
import org.example.btl.model.Genre;
import org.example.btl.model.User;
import org.example.btl.service.BorrowService;
import org.example.btl.service.NotificationService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class BookInfoController implements Initializable {
    private Document document;
    private User user;

    private BorrowService borrowService = new BorrowService();
    private NotificationService notificationService = new NotificationService();
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
    private TextArea descriptionText;

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    private UserSearchBookController userSearchBookController;

    private UserReturnBookController userReturnBookController;

    public void setUserSearchBookController(UserSearchBookController userSearchBookController) {
        this.userSearchBookController = userSearchBookController;
    }

    public void setUserReturnBookController(UserReturnBookController userReturnBookController) {
        this.userReturnBookController = userReturnBookController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void handleBorrow(ActionEvent event) {
        String validateMess = borrowService.validateBorrow(user, document);
        if (validateMess != null) {
            alert.setContentText(validateMess);
            alert.show();
        } else {
            user = borrowService.borrowDocument(user, document);

            user = notificationService.addNotification(user, "Document Borrowed Successfully",
                    "You have successfully borrowed the document titled '" + document.getTitle() + "'.");

            userSearchBookController.setUser(user);
            userSearchBookController.setUserInfo();
            userSearchBookController.refresh();

            alert.setContentText("Document borrowed successfully! Thank you for using our library.");
            alert.show();
        }
    }

    public void setBookInfo() {
        String authors = document.getAuthors().stream()
                .map(Author::getName)
                .collect(Collectors.joining(", "));

        String genres = document.getGenres().stream()
                .map(Genre::getName)
                .collect(Collectors.joining(", "));

        String imageLink = document.getImageLink();

        if (imageLink != null) {
            thumbnail.setImage(new Image(document.getImageLink()));
        } else {
            thumbnail.setImage(new Image(getClass().getResource("/org/example/btl/images/no-photo.png").toExternalForm()));
        }

        titleText.setText(document.getTitle());
        authorText.setText(authors);
        genreText.setText(genres);
        descriptionText.setText(document.getDescription());
        quantityText.setText(String.valueOf(document.getQuantity()));

        if (document.getPublisher() != null) {
            publisherText.setText(document.getPublisher().getName());
        } else {
            publisherText.setText("Not available");
        }
    }

    public void handleReturn(ActionEvent event) {
        userReturnBookController.handleReturnBook(event);
        userReturnBookController.setUserInfo();
        userReturnBookController.refresh();
    }
}
