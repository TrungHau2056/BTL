package org.example.btl.controller.admincontrollers;

import com.google.api.services.books.v1.model.Volume;
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

    public AddByISBNController() throws GeneralSecurityException, IOException {
    }

    @Override
    public void setAdminInfo() {

    }

    public void handleCheck() throws IOException {
        String isbn = isbnText.getText();
        volumeInfo = googleBooksService.searchByISBN(isbn);
        if (volumeInfo == null) {
            alertErr.setContentText("Not found document matching ISBN!");
            alertErr.show();
        } else {
            titleText.setText(volumeInfo.getTitle());
            descriptionText.setText(volumeInfo.getDescription());
            publisherText.setText(volumeInfo.getPublisher());

            List<String> authors = volumeInfo.getAuthors();
            String authorsStr = "";
            for (int i = 0; i < authors.size() - 1; i++) {
                authorsStr += authors.get(i);
                authorsStr += ", ";
            }
            authorsStr += authors.getLast();

            List<String> genres = volumeInfo.getCategories();
            String genresStr = "";
            for (int i = 0; i < genres.size() - 1; i++) {
                genresStr += genres.get(i);
                genresStr += ", ";
            }
            genresStr += genres.getLast();

            authorText.setText(authorsStr);
            genreText.setText(genresStr);

            thumbnail.setImage(new Image(volumeInfo.getImageLinks().getThumbnail()));
        }
    }

    public void handleAdd() {
        if (volumeInfo == null) {
            alertErr.setContentText("Please check before add!");
            alertErr.show();
        } else {
            String title = titleText.getText();
            String publisherName = publisherText.getText();
            String description = descriptionText.getText();
            String imageLink = volumeInfo.getImageLinks().getThumbnail();
            String quantityStr = quantityText.getText();
            List<String> authorNames = volumeInfo.getAuthors();
            List<String> genreNames = volumeInfo.getCategories();

            String validateMess = documentService.validateAdd(title, authorNames, genreNames, publisherName, quantityStr, description);
            if (validateMess != null) {
                alertErr.setContentText(validateMess);
                alertErr.show();
            } else {
                Document document = new Document(title, description, Integer.parseInt(quantityStr), imageLink);
                documentService.saveWithAdminAuthorsPublisherGenre(document, admin, authorNames, publisherName, genreNames);

                alertInfo.setContentText("Document successfully saved!");
                alertInfo.show();
            }
        }
    }
}
