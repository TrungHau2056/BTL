package org.example.btl.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.btl.controller.usercontrollers.UserReturnBookController;
import org.example.btl.controller.usercontrollers.UserSearchBookController;
import org.example.btl.model.*;
import org.example.btl.service.BorrowService;
import org.example.btl.service.DocumentService;
import org.example.btl.service.NotificationService;
import org.example.btl.service.RatingService;

import java.util.List;
import java.util.stream.Collectors;

public class BookInfoController {
    private Document document;
    private User user;

    private UserSearchBookController userSearchBookController;

    private UserReturnBookController userReturnBookController;

    private BorrowService borrowService = new BorrowService();
    private RatingService ratingService = new RatingService();
    private NotificationService notificationService = new NotificationService();
    private DocumentService documentService = new DocumentService();
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

    public void setUserSearchBookController(UserSearchBookController userSearchBookController) {
        this.userSearchBookController = userSearchBookController;
    }

    public void setUserReturnBookController(UserReturnBookController userReturnBookController) {
        this.userReturnBookController = userReturnBookController;
    }

    /**
     * set user for scene.
     */
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
            thumbnail.setImage(
                    new Image(getClass().getResource("/org/example/btl/images/no-photo.png")
                            .toExternalForm())
            );
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

        List<Rating> ratings = documentService.getRatings(document);

        //calculate avg + update numOfRating
    }

    /**
     * click button borrow.
     */
    public void handleBorrow(ActionEvent event) {
        if (user != userSearchBookController.getUser()) {
            alert.setContentText("You have already borrowed this document");
            alert.show();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
            return;
        }

        String validateMess = borrowService.validateBorrow(user, document);
        if (validateMess != null) {
            alert.setContentText(validateMess);
            alert.show();
        } else {
            user = borrowService.borrowDocument(user, document);

            user = notificationService.addNotification(user,
                    "Document Borrowed Successfully",
                    "You have successfully borrowed the document titled '"
                            + document.getTitle()
                            + "'."
            );

            quantityText.setText(String.valueOf(Integer.parseInt(quantityText.getText()) - 1));

            userSearchBookController.setUser(user);
            userSearchBookController.setUserInfo();

            alert.setContentText("Document borrowed successfully!");
            alert.show();
        }
    }

    /**
     * click return button.
     *
     * @param event the action event triggered by the user.
     */
    public void handleReturn(ActionEvent event) {
        if (user != userReturnBookController.getUser()) {
            alert.setContentText("You have already returned this document");
            alert.show();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
            return;
        }

        user = borrowService.returnDocument(user, document);

        user = notificationService.addNotification(user,
                "Document Returned Successfully",
                "You have successfully returned the document titled '"
                        + document.getTitle()
                        + "'."
        );
        userReturnBookController.setUser(user);
        userReturnBookController.setUserInfo();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        alert.setContentText("Return Successfully!");
        alert.show();
    }

    public void handleRate() {
        // score
        int score = 10000;

        document = ratingService.updateOrAddRating(user, document, score);

        alert.setContentText("Successfully rated");
        alert.show();

        List<Rating> ratings = documentService.getRatings(document);

        //calculate avg + update numOfRating
    }
}
