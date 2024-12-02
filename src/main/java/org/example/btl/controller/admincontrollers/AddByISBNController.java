package org.example.btl.controller.admincontrollers;

import com.google.api.services.books.v1.model.Volume;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.btl.model.Document;
import org.example.btl.service.GoogleBooksService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class AddByISBNController extends AdminBaseController {
    private GoogleBooksService googleBooksService = GoogleBooksService.getInstance();
    private Volume.VolumeInfo volumeInfo;

    @FXML
    private ImageView thumbnail;
    @FXML
    private TextField isbnText;
    @FXML
    private TextField titleText;
    @FXML
    private TextField quantityText;
    @FXML
    private TextField genreText;
    @FXML
    private TextArea descriptionText;
    @FXML
    private TextField authorText;
    @FXML
    private TextField publisherText;

    /**
     * Constructor for the AddByISBNController.
     * Initializes the controller and handles any required security or I/O setup.
     * @throws GeneralSecurityException if a security issue occurs during initialization
     * @throws IOException if an I/O error occurs during initialization
     */
    public AddByISBNController() throws GeneralSecurityException, IOException {
    }

    /**
     * Sets the admin's display information by updating the name label.
     */
    @Override
    public void setAdminInfo() {
        nameLabel.setText("Hi " + admin.getName());
    }

    /**
     * Handles the check action triggered by the user.
     * Searches for book information by ISBN through the Google Books API and updates the UI with the results.
     * Displays an error message if no document is found matching the ISBN.
     */
    public void handleCheck() {
        String isbn = isbnText.getText();

        Task<Volume.VolumeInfo> callAPITask = new Task<>() {
            @Override
            protected Volume.VolumeInfo call() throws Exception {
                return googleBooksService.searchByISBN(isbn);
            }
        };

        callAPITask.setOnSucceeded(e -> {
            volumeInfo = callAPITask.getValue();
            if (volumeInfo == null) {
                alertErr.setContentText("Not found document matching ISBN!");
                alertErr.show();
            } else {
                titleText.setText(volumeInfo.getTitle());
                descriptionText.setText(volumeInfo.getDescription());
                publisherText.setText(volumeInfo.getPublisher());

                List<String> authors = volumeInfo.getAuthors();
                String authorsStr = authors == null ? "" : String.join(", ", authors);

                List<String> genres = volumeInfo.getCategories();
                String genresStr = genres == null ? "" : String.join(", ", genres);

                authorText.setText(authorsStr);
                genreText.setText(genresStr);
                if (volumeInfo.getImageLinks() != null &&
                        volumeInfo.getImageLinks().getThumbnail() != null) {
                    thumbnail.setImage(new Image(volumeInfo.getImageLinks().getThumbnail()));
                } else {
                    thumbnail.setImage(new Image(getClass().getResource("/org/example/btl/images/ImageNotAvailable.jpg").toExternalForm()));
                }
            }
        });

        callAPITask.setOnFailed(e -> {
            Throwable exception = callAPITask.getException();
            if (exception != null) {
                exception.printStackTrace();
            }
        });

        new Thread(callAPITask).start();
    }

    /**
     * Handles the add action triggered by the user.
     * Validates the document data, checks for required fields (author, genre), and adds the document if valid.
     * Displays error messages if any required field is missing or invalid, and success message if the document is successfully added.
     */
    public void handleAdd() {
        if (volumeInfo == null) {
            alertErr.setContentText("Please check before add!");
            alertErr.show();
        } else if (volumeInfo.getAuthors() == null) {
            alertErr.setContentText("This document can't be added because it doesn't have an author!");
            alertErr.show();
        } else if (volumeInfo.getCategories() == null) {
            alertErr.setContentText("This document can't be added because it doesn't have a genre!");
            alertErr.show();
        } else {
            String title = titleText.getText();
            String publisherName = publisherText.getText() == null ? "" : publisherText.getText();
            String description = descriptionText.getText() == null ? "Not available" : descriptionText.getText();
            String imageLink = null;
            if (volumeInfo.getImageLinks() != null &&
                    volumeInfo.getImageLinks().getThumbnail() != null) {
                imageLink = volumeInfo.getImageLinks().getThumbnail();
            }
            String quantityStr = quantityText.getText();
            List<String> authorNames = volumeInfo.getAuthors();
            List<String> genreNames = volumeInfo.getCategories();

            String validateMess = documentService.validateAddDoc(title, authorNames, genreNames, quantityStr, description);
            if (validateMess != null) {
                alertErr.setContentText(validateMess);
                alertErr.show();
            } else {
                Document document = new Document(title, description, Integer.parseInt(quantityStr), imageLink, true);
                documentService.saveWithAdminAuthorsPublisherGenre(document, admin, authorNames, publisherName, genreNames);

                alertInfo.setContentText("Document successfully saved!");
                alertInfo.show();
            }
        }
    }
}
