package org.example.btl.controller.admincontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import org.example.btl.model.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdminAddBookController extends AdminBaseController {
    @FXML
    private ToggleButton NormalButton;

    @FXML
    private TextField titleText;
    @FXML
    private TextField quantityText;

    @FXML
    private TextField genresText;
    @FXML
    private Label genreLabel;
    List<String> genreNames = new ArrayList<>();

    @FXML
    private TextArea descriptionText;

    @FXML
    private TextField authorsText;
    @FXML
    private Label authorLabel;
    private List<String> authorNames = new ArrayList<>();

    @FXML
    private TextField publisherText;
    @FXML
    private TextField imageLinkText;

    /**
     * Initializes the UI components.
     * Sets the default state of the NormalButton to selected.
     */
    @FXML
    public void initialize() {
        NormalButton.setSelected(true);
    }

    /**
     * Sets the admin's information on the UI by updating the name label with a greeting.
     */
    @Override
    public void setAdminInfo() {
        nameLabel.setText("Hi " + admin.getName());
    }

    /**
     * Adds a new genre to the list if it's not already present.
     * Displays an error message if the genre is empty or already added.
     * Updates the genre label with the new list of genres.
     */
    public void genreAdd() {
        String genre = genresText.getText();

        if (genreNames.contains(genre)) {
            alertErr.setContentText("Genre already added.");
            alertErr.show();
        } else if (Objects.equals(genre, "")) {
            alertErr.setContentText("Please enter genre.");
            alertErr.show();
        } else {
            genreNames.add(genre);
            String genres = "";
            for (int i = 0; i < genreNames.size() - 1; i++) {
                genres += genreNames.get(i);
                genres += ", ";
            }
            genres += genreNames.getLast();
            genres += ".";

            genreLabel.setText(genres);
            genresText.setText("");
        }
    }

    /**
     * Deletes the last genre from the list.
     * Updates the genre label to reflect the remaining genres.
     * If the genre list is empty after deletion, the label is cleared.
     */
    public void genreDelete() {
        if (genreNames.isEmpty()) {
            return;
        }

        genreNames.removeLast();
        if (genreNames.isEmpty()) {
            genreLabel.setText("");
            return;
        }

        String genres = "";
        for (int i = 0; i < genreNames.size() - 1; i++) {
            genres += genreNames.get(i);
            genres += ", ";
        }
        genres += genreNames.getLast();
        genres += ".";

        genreLabel.setText(genres);
    }

    /**
     * Adds a new author to the list if it's not already present.
     * Displays an error message if the author is empty or already added.
     * Updates the author label with the new list of authors.
     */
    public void authorAdd() {
        String author = authorsText.getText();

        if (authorNames.contains(author)) {
            alertErr.setContentText("Author already added.");
            alertErr.show();
        } else if (Objects.equals(author, "")) {
            alertErr.setContentText("Please enter author.");
            alertErr.show();
        } else {
            authorNames.add(author);
            String authors = "";
            for (int i = 0; i < authorNames.size() - 1; i++) {
                authors += authorNames.get(i);
                authors += ", ";
            }
            authors += authorNames.getLast();
            authors += ".";

            authorLabel.setText(authors);
            authorsText.setText("");
        }
    }

    /**
     * Deletes the last author from the list.
     * Updates the author label to reflect the remaining authors.
     * If the author list is empty after deletion, the label is cleared.
     */
    public void authorDelete() {
        if (authorNames.isEmpty()) {
            return;
        }

        authorNames.removeLast();
        if (authorNames.isEmpty()) {
            authorLabel.setText("");
            return;
        }

        String authors = "";
        for (int i = 0; i < authorNames.size() - 1; i++) {
            authors += authorNames.get(i);
            authors += ", ";
        }
        authors += authorNames.getLast();
        authors += ".";

        authorLabel.setText(authors);
    }

    /**
     * Handles the action of adding a new document.
     * Validates the input data (title, authors, genres, quantity, description, and image link) and adds the document if valid.
     * Displays an error message if any validation fails, including invalid image link, and a success message if the document is successfully saved.
     *
     * @param event The event that triggers the action.
     */
    public void handleAdd(ActionEvent event) {
        String title = titleText.getText();
        String publisherName = publisherText.getText();
        String description = Objects.equals(descriptionText.getText(), "") ? "Not available" : descriptionText.getText();
        String imageLink = imageLinkText.getText();
        String quantityStr = quantityText.getText();

        if (!Objects.equals(imageLink, "")) {
            try {
                new Image(imageLink);
            } catch (Exception e) {
                alertErr.setContentText("Invalid link! Please try again");
                alertErr.show();
                return;
            }
        } else {
            imageLink = null;
        }

        String validateMess = documentService.validateAddDoc(title, authorNames, genreNames, quantityStr, description);
        if (validateMess != null) {
            alertErr.setContentText(validateMess);
            alertErr.show();
        } else {
            Document document = new Document(title, description, Integer.parseInt(quantityStr), imageLink, false);
            documentService.saveWithAdminAuthorsPublisherGenre(document, admin, authorNames, publisherName, genreNames);

            alertInfo.setContentText("Document successfully saved!");
            alertInfo.show();
        }
    }
}
