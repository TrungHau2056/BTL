package org.example.btl.controller.usercontrollers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import org.example.btl.model.Author;
import org.example.btl.model.Borrow;

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
    private TableView<Borrow> tableView;

    @FXML
    private TableColumn<Borrow, Integer> idCol;
    @FXML
    private TableColumn<Borrow, String> titleCol;
    @FXML
    private TableColumn<Borrow, String> authorsCol;
    @FXML
    private TableColumn<Borrow, Date> borrowDateCol;
    @FXML
    private TableColumn<Borrow, Date> returnDateCol;

    private ObservableList<Borrow> borrowObservableList;

    /**
     * init.
     *
     * @param url the location used to resolve relative paths for the root object.
     * @param resourceBundle the resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        historyButton.setSelected(true);

        idCol.setCellValueFactory(data ->
                new SimpleIntegerProperty(
                        data.getValue().getDocument() == null
                                ? 0
                                : data.getValue().getDocument().getId()
                ).asObject()
        );

        titleCol.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getDocument() == null
                                ? "Not available"
                                : data.getValue().getDocument().getTitle()
                )
        );

        authorsCol.setCellValueFactory(data -> {
            if (data.getValue().getDocument() == null) {
                return new SimpleStringProperty("Not available");
            }
            Set<Author> authors = data.getValue().getDocument().getAuthors();
            String authorsString = authors.stream()
                    .map(Author::getName)
                    .collect(Collectors.joining(", "));
            return new SimpleStringProperty(authorsString);
        });

        borrowDateCol.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        returnDateCol.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
    }

    /**
     * set user for scene.
     */
    @Override
    public void setUserInfo() {
        nameLabel.setText(user.getName());
        byte[] avatarData = user.getAvatar();
        if (avatarData != null) {
            InputStream inputStream = new ByteArrayInputStream(avatarData);
            avatar.setImage(new Image(inputStream));
        }

        Task<List<Borrow>> loadDocTask = new Task<>() {
            @Override
            protected List<Borrow> call() {
                return borrowService.findDocHasReturned(user);
            }
        };

        loadDocTask.setOnSucceeded(e -> {
            borrowObservableList = FXCollections.observableArrayList(loadDocTask.getValue());
            tableView.setItems(borrowObservableList);
        });

        loadDocTask.setOnFailed(e -> {
            Throwable exception = loadDocTask.getException();
            if (exception != null) {
                exception.printStackTrace();
            }
        });

        new Thread(loadDocTask).start();
    }
}
