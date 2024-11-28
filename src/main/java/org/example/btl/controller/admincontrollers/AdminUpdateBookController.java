package org.example.btl.controller.admincontrollers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

    @Override
    public void setAdminInfo() {
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

    public void handleUpdateTitle() {
        titleText.setDisable(false);
        updateButton.setDisable(false);
    }

    public void handleUpdateQuantity() {
        quantityText.setDisable(false);
        updateButton.setDisable(false);
    }

    public void handleUpdateGenre() {
        genresText.setDisable(false);
        genreAddBtn.setDisable(false);
        genreDeleteBtn.setDisable(false);
        updateButton.setDisable(false);
    }

    public void handleUpdateAuthor() {
        authorsText.setDisable(false);
        authorAddBtn.setDisable(false);
        authorDeleteBtn.setDisable(false);
        updateButton.setDisable(false);
    }

    public void handleUpdateDescription() {
        descriptionText.setDisable(false);
        updateButton.setDisable(false);
    }

    public void handleUpdatePublisher() {
        publisherText.setDisable(false);
        updateButton.setDisable(false);
    }

    public void handleUpdateImageLink() {
        imageLinkText.setDisable(false);
        updateButton.setDisable(false);
    }

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

    public void handleUpdate() {
        if (document.isAddedByISBN()) {
            String quantityStr = quantityText.getText();

            try {
                int quantity = Integer.parseInt(quantityStr);
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
                    Image image = new Image(imageLink);
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

                documentService.updateDocument(document, authorNames, publisherName, genreNames);

                alertInfo.setContentText("Document successfully updated!");
                alertInfo.show();

                titleText.setDisable(true);
                quantityText.setDisable(true);
                genresText.setDisable(true);
                descriptionText.setDisable(true);
                authorsText.setDisable(true);
                authorLabel.setDisable(true);
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
