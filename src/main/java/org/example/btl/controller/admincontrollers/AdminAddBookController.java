package org.example.btl.controller.admincontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import org.example.btl.model.Document;

import java.util.ArrayList;
import java.util.List;

public class AdminAddBookController extends AdminBaseController {

    @FXML
    ToggleButton NormalButton;

    @FXML
    public void initialize() {
        NormalButton.setSelected(true);
    }

    @Override
    public void setAdminInfo() {

    }

    public void handleAdd(ActionEvent event) {
        String title = "";
        List<String> authorNames = new ArrayList<>();
        List<String> genresNames = new ArrayList<>();
        String publisherName = "";
        String description = "";
        String imageLink = "";
        String quantityStr = "";

        String validateMess = documentService.validateAdd(title, authorNames, genresNames, publisherName, quantityStr, description);
        if (validateMess != null) {
            alertErr.setContentText(validateMess);
            alertErr.show();
        } else {
            Document document = new Document(title, description, Integer.parseInt(quantityStr), imageLink, "");
            documentService.saveWithAdminAuthorsPublisherGenre(document, admin, authorNames, publisherName, genresNames);

            alertInfo.setContentText("Document successfully saved!");
            alertInfo.show();
        }
    }

}
