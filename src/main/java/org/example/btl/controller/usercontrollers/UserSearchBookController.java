package org.example.btl.controller.usercontrollers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.btl.controller.BookInfoController;
import org.example.btl.model.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class UserSearchBookController extends UserBaseController implements Initializable {
    @FXML
    private TextField searchText;

    @FXML
    private ChoiceBox<String> criteria;
    @FXML
    private ChoiceBox<String> statuses;

    @FXML
    private TableView<Document> tableView;
    @FXML
    private TableColumn<Document, Integer> idCol;
    @FXML
    private TableColumn<Document, String> titleCol;
    @FXML
    private TableColumn<Document, String> authorsCol;
    @FXML
    private TableColumn<Document, String> genresCol;
    @FXML
    private TableColumn<Document, String> publisherCol;
    @FXML
    private TableColumn<Document, String> descriptionCol;
    @FXML
    private TableColumn<Document, String> statusCol;

    private ObservableList<Document> documentObservableList;

    /**
     * init.
     * @param url the location used to resolve relative paths for the root object.
     * @param resourceBundle the resources used to localize the root object.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        criteria.getItems().addAll("Title", "Author", "Genre", "Publisher");
        criteria.setValue("Title");
        statuses.getItems().addAll("All", "Borrowed", "Not Borrowed");
        statuses.setValue("All");

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleCol.setCellFactory(_ -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(item);
                    Tooltip tooltip = new Tooltip(item);
                    setTooltip(tooltip);
                }
            }
        });

        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        authorsCol.setCellValueFactory(data -> {
            Set<Author> authors = data.getValue().getAuthors();
            String authorsString = authors.stream()
                    .map(Author::getName)
                    .collect(Collectors.joining(", "));
            return new SimpleStringProperty(authorsString);
        });

        genresCol.setCellValueFactory(data -> {
            Set<Genre> genres = data.getValue().getGenres();
            String genresString = genres.stream()
                    .map(Genre::getName)
                    .collect(Collectors.joining(", "));
            return new SimpleStringProperty(genresString);
        });

        publisherCol.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getPublisher() != null
                        ? data.getValue().getPublisher().getName() : "Not available"
        ));

        statusCol.setCellValueFactory(data -> {
            Document document = data.getValue();
            String status = borrowService.isCurrentlyBorrowing(user, document)
                    ? "Borrowed" : "Not Borrowed";
            return new SimpleStringProperty(status);
        });
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

        Task<List<Document>> loadDocTask = new Task<>() {
            @Override
            protected List<Document> call() {
                return documentService.findAll();
            }
        };

        loadDocTask.setOnSucceeded(e -> {
            documentObservableList = FXCollections.observableArrayList(loadDocTask.getValue());
            tableView.setItems(documentObservableList);
        });

        loadDocTask.setOnFailed(e -> {
            Throwable exception = loadDocTask.getException();
            if (exception != null) {
                exception.printStackTrace();
            }
        });

        new Thread(loadDocTask).start();
    }

    /**
     * click document in table.
     */

    public void handleTableClick() throws IOException {
        Document selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
             return;
        }

        String fxmlFile = "/org/example/btl/view/bookInfo-view.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();

        BookInfoController controller = loader.getController();
        controller.setDocument(selectedItem);
        controller.setUser(user);
        controller.setUserSearchBookController(this);
        controller.setBookInfo();

        Stage stage = new Stage();
        stage.setTitle("Document");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * click search button.
     */

    public void handleUserSearch() {
        String keyword = searchText.getText();
        String criterion = criteria.getValue();
        String status = statuses.getValue();

        String validateMessage = documentService.validateSearchByKeyword(keyword);
        if (validateMessage != null) {
            alertErr.setContentText(validateMessage);
            alertErr.show();
        } else {
            Task<List<Document>> searchDocTask = new Task<>() {
                @Override
                protected List<Document> call() {
                    return switch (criterion) {
                        case "Title" -> documentService.searchByTitle(keyword, user, status);
                        case "Author" -> documentService.searchByAuthor(keyword, user, status);
                        case "Genre" -> documentService.searchByGenre(keyword, user, status);
                        case "Publisher" -> documentService.searchByPublisher(keyword, user, status);
                        default -> null;
                    };
                }
            };

            searchDocTask.setOnSucceeded(e -> {
                List<Document> documents = searchDocTask.getValue();

                if (documents.isEmpty()) {
                    alertErr.setContentText("No search results match the keyword.");
                    alertErr.show();
                } else {
                    documentObservableList = FXCollections.observableArrayList(documents);
                    tableView.setItems(documentObservableList);
                }
            });

            searchDocTask.setOnFailed(e -> {
                Throwable exception = searchDocTask.getException();
                if (exception != null) {
                    exception.printStackTrace();
                }
            });

            new Thread(searchDocTask).start();
        }
    }
}
