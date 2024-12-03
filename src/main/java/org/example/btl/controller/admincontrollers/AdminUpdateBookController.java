package org.example.btl.controller.admincontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import org.example.btl.model.Author;
import org.example.btl.model.Document;
import org.example.btl.model.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdminUpdateBookController extends AdminBaseController {
    private Document document;

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

    @FXML
    private Label warnLabel;
    @FXML
    private Button updateButton;
    @FXML
    private Button updateTitleBtn;
    @FXML
    private Button updateGenreBtn;
    @FXML
    private Button updateAuthorBtn;
    @FXML
    private Button updateDescriptionBtn;
    @FXML
    private Button updatePublisherBtn;
    @FXML
    private Button updateImagelinkBtn;
    @FXML
    private Button authorAddBtn;
    @FXML
    private Button authorDeleteBtn;
    @FXML
    private Button genreAddBtn;
    @FXML
    private Button genreDeleteBtn;


    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    /**
     * Sets the information for the admin to view and edit the details of a document.
     * This method populates various text fields and labels with the document's data,
     * including title, quantity, description, image link, publisher, authors, and genres.
     * If the document has an associated publisher, its name is displayed.
     * The authors and genres are displayed as comma-separated lists.
     * If the document was added by ISBN, the method disables the editing buttons
     * and displays a warning label to prevent further updates.
     */
    @Override
    public void setAdminInfo() {
        nameLabel.setText("Hi " + admin.getName());
        titleText.setText(document.getTitle());
        quantityText.setText(String.valueOf(document.getQuantity()));
        descriptionText.setText(document.getDescription());
        imageLinkText.setText(document.getImageLink());

        if (document.getPublisher() != null) {
            publisherText.setText(document.getPublisher().getName());
        }

        for (Author author : document.getAuthors()) {
            authorNames.add(author.getName());
        }
        String authors = "";
        for (int i = 0; i < authorNames.size() - 1; i++) {
            authors += authorNames.get(i);
            authors += ", ";
        }
        authors += authorNames.getLast();
        authors += ".";
        authorLabel.setText(authors);

        for (Genre genre : document.getGenres()) {
            genreNames.add(genre.getName());
        }
        String genres = "";
        for (int i = 0; i < genreNames.size() - 1; i++) {
            genres += genreNames.get(i);
            genres += ", ";
        }
        genres += genreNames.getLast();
        genres += ".";
        genreLabel.setText(genres);


        if (document.isAddedByISBN()) {
            warnLabel.setVisible(true);
            updateTitleBtn.setDisable(true);
            updateGenreBtn.setDisable(true);
            updateAuthorBtn.setDisable(true);
            updateDescriptionBtn.setDisable(true);
            updatePublisherBtn.setDisable(true);
            updateImagelinkBtn.setDisable(true);
        }
    }

    /**
     * Enables the title text field and the update button to allow the admin to edit
     * the title of the document. This method is called when the admin wants to update
     * the title of the document.
     */
    public void handleUpdateTitle() {
        titleText.setDisable(false);
        updateButton.setDisable(false);
    }

    /**
     * Enables the quantity text field and the update button to allow the admin to edit
     * the quantity of the document. This method is called when the admin wants to update
     * the quantity of the document.
     */
    public void handleUpdateQuantity() {
        quantityText.setDisable(false);
        updateButton.setDisable(false);
    }

    /**
     * Enables the genre text field, genre add button, genre delete button,
     * and the update button to allow the admin to edit the genres of the document.
     * This method is called when the admin wants to update the genres of the document.
     */
    public void handleUpdateGenre() {
        genresText.setDisable(false);
        genreAddBtn.setDisable(false);
        genreDeleteBtn.setDisable(false);
        updateButton.setDisable(false);
    }

    /**
     * Enables the author text field, author add button, author delete button,
     * and the update button to allow the admin to edit the authors of the document.
     * This method is called when the admin wants to update the authors of the document.
     */
    public void handleUpdateAuthor() {
        authorsText.setDisable(false);
        authorAddBtn.setDisable(false);
        authorDeleteBtn.setDisable(false);
        updateButton.setDisable(false);
    }

    /**
     * Enables the description text field and the update button to allow the admin
     * to edit the description of the document. This method is called when the admin
     * wants to update the description of the document.
     */
    public void handleUpdateDescription() {
        descriptionText.setDisable(false);
        updateButton.setDisable(false);
    }

    /**
     * Enables the publisher text field and the update button to allow the admin
     * to edit the publisher of the document. This method is called when the admin
     * wants to update the publisher of the document.
     */
    public void handleUpdatePublisher() {
        publisherText.setDisable(false);
        updateButton.setDisable(false);
    }

    /**
     * Enables the image link text field and the update button to allow the admin
     * to edit the image link of the document. This method is called when the admin
     * wants to update the image link of the document.
     */
    public void handleUpdateImageLink() {
        imageLinkText.setDisable(false);
        updateButton.setDisable(false);
    }

    /**
     * Adds a new genre to the list of genres for the document. If the genre is
     * already added, or if the genre input field is empty, an error message is shown.
     * Otherwise, the genre is added to the list and displayed in the genre label.
     *
     * @throws IllegalArgumentException if the genre is already in the list or empty.
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
     * Deletes the last genre from the list of genres for the document. If there are no genres,
     * the method does nothing. After deleting, the genre list is updated and displayed in the genre label.
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
     * Adds a new author to the list of authors for the document.
     * If the author is already in the list, an error message is shown.
     * If the input is empty, the user is prompted to enter an author.
     * After adding the author, the list is updated and displayed in the author label.
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
     * Deletes the last author from the list of authors for the document.
     * If there are no authors in the list, the method does nothing.
     * After deleting an author, the list is updated and displayed in the author label.
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
     * Handles the update operation for the document.
     * If the document is added by ISBN, only the quantity can be updated.
     * The quantity field must be a valid integer.
     * If the document is not added by ISBN, it allows updating the title, publisher, description, image link, quantity, authors, and genres.
     * The method validates the input and updates the document accordingly.
     *
     * If the input is valid, the document is updated and a success message is shown.
     * If there are any issues with the input, an error message is displayed.
     */
    public void handleUpdate() {
        if (document.isAddedByISBN()) {
            String quantityStr = quantityText.getText();

            try {
                Integer.parseInt(quantityStr);
            } catch (NumberFormatException e) {
                alertErr.setContentText("Quantity field must be a number!");
                alertErr.show();
                return;
            }

            document.setQuantity(Integer.parseInt(quantityStr));
            documentService.update(document);

            alertInfo.setContentText("Document successfully updated!");
            alertInfo.show();
        } else {
            String title = titleText.getText();
            String publisherName = publisherText.getText();
            String description = Objects.equals(descriptionText.getText(), "") ? "Not available" : descriptionText.getText();
            String imageLink = imageLinkText.getText();
            String quantityStr = quantityText.getText();

            if (imageLink != null && !Objects.equals(imageLink, "")) {
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

            String validateMess = documentService.validateUpdateDoc(title, authorNames, genreNames, quantityStr, description);
            if (validateMess != null) {
                alertErr.setContentText(validateMess);
                alertErr.show();
            } else {
                document.setTitle(title);
                document.setDescription(description);
                document.setImageLink(imageLink);
                document.setQuantity(Integer.parseInt(quantityStr));

                document = documentService.updateDocument(document, authorNames, publisherName, genreNames);

                alertInfo.setContentText("Document successfully updated!");
                alertInfo.show();

                titleText.setDisable(true);
                quantityText.setDisable(true);
                genresText.setDisable(true);
                descriptionText.setDisable(true);
                authorsText.setDisable(true);
                publisherText.setDisable(true);
                imageLinkText.setDisable(true);
                updateButton.setDisable(true);
                authorAddBtn.setDisable(true);
                authorDeleteBtn.setDisable(true);
                genreAddBtn.setDisable(true);
                genreDeleteBtn.setDisable(true);
            }
        }
    }
}
