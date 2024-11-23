package org.example.btl.controller.usercontrollers;


import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import org.example.btl.model.Author;
import org.example.btl.model.Borrow;
import org.example.btl.model.Document;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class UserReturnHistoryController extends UserBaseController implements Initializable {
    @FXML
    private ToggleButton historyButton;

    @FXML
    private TableView<Document> tableView;

    @FXML
    private TableColumn<Document, Integer> idCol;
    @FXML
    private TableColumn<Document, String> titleCol;
    @FXML
    private TableColumn<Document, String> authorsCol;
    @FXML
    private TableColumn<Document, Date> borrowDateCol;
    @FXML
    private TableColumn<Document, Date> returnDateCol;

    private ObservableList<Document> documentObservableList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        historyButton.setSelected(true);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        authorsCol.setCellValueFactory(data -> {
            Set<Author> authors = data.getValue().getAuthors();
            String authorsString = authors.stream()
                    .map(Author::getName)
                    .collect(Collectors.joining(", "));
            return new SimpleStringProperty(authorsString);
        });

        borrowDateCol.setCellValueFactory(data -> {
            Borrow borrow = borrowService.findByUserCurrentlyBorrowsDocument(user, data.getValue());
            return new SimpleObjectProperty<>(borrow != null ? borrow.getBorrowDate() : null);
        });

        returnDateCol.setCellValueFactory(data -> {
            Borrow borrow = borrowService.findByUserCurrentlyBorrowsDocument(user, data.getValue());
            return new SimpleObjectProperty<>(borrow != null ? borrow.getReturnDate() : null);
        });
    }

    public void switchToReturnScene(ActionEvent event) {

    }


    @Override
    public void setUserInfo() {
        nameLabel.setText(user.getName());
        byte[] avatarData = user.getAvatar();
        if (avatarData != null) {
            InputStream inputStream = new ByteArrayInputStream(avatarData);
            avatar.setImage(new Image(inputStream));
        }


        Task<List<Document>> loadDocTask = new Task<>() {
            @Override
            protected List<Document> call() throws Exception {
                return documentService.findDocHasReturned(user);
            }
        };

        loadDocTask.setOnSucceeded(e -> {
            documentObservableList = FXCollections.observableArrayList(loadDocTask.getValue());
            tableView.setItems(documentObservableList);
        });

        loadDocTask.setOnFailed(e -> {
            System.out.println("Failed");
            alertErr.setContentText("Error: " + loadDocTask.getException().getMessage());
            alertErr.show();
        });

        new Thread(loadDocTask).start();
    }
}
