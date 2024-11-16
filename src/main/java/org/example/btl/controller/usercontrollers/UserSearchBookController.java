package org.example.btl.controller.usercontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.example.btl.model.Author;
import org.example.btl.model.Document;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class UserSearchBookController extends UserBaseController implements Initializable {
    @FXML
    private TextField titleText;

    @FXML
    private ChoiceBox<String> TypeBook;

    @FXML
    private TableView<Document> tableView = new TableView();
    @FXML
    private TableColumn<Document, Integer> idCol;
    @FXML
    private TableColumn<Document, String> titleCol;
    @FXML
    private TableColumn<Document, String> descriptionCol;
    @FXML
    private TableColumn<Document, Set<Author>> authorsCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void setUserInfo() {

    }

    public void handleUserSearch(ActionEvent event) {
        String title = titleText.getText();
        String validateMessage = documentService.validateSearchByTitle(title);
        if (validateMessage != null) {
            alertErr.setContentText(title);
            alertErr.show();
        } else {
            List<Document> documents = documentService.searchByTitleKeyword(title);
            //
        }
    }
}
