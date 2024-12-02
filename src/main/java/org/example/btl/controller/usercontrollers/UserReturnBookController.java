package org.example.btl.controller.usercontrollers;

import javafx.beans.property.SimpleObjectProperty;
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
import org.example.btl.model.Author;
import org.example.btl.model.Borrow;
import org.example.btl.model.Document;
import org.example.btl.model.Genre;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class UserReturnBookController extends UserBaseController implements Initializable {
    @FXML
    private TextField searchText;
    @FXML
    private ToggleButton returnButton;

    @FXML
    private ChoiceBox<String> criteria;
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
    private TableColumn<Document, Date> borrowDateCol;

    private ObservableList<Document> documentObservableList;

    /**
     * init.
     *
     * @param url the location used to resolve relative paths for the root object.
     * @param resourceBundle the resources used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        returnButton.setSelected(true);

        criteria.getItems().addAll("Title", "Author", "Genre", "Publisher");
        criteria.setValue("Title");

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

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

        borrowDateCol.setCellValueFactory(data -> {
            Borrow borrow = borrowService.findByUserCurrentlyBorrowsDocument(user, data.getValue());
            return new SimpleObjectProperty<>(borrow != null ? borrow.getBorrowDate() : null);
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
                return documentService.findDocCurrentlyBorrow(user);
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
     * click search button.
     */
    public void handleSearchBook() {
        String keyword = searchText.getText();
        String criterion = criteria.getValue();
        String status = "Borrowed";

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

    /**
     * click remove button.
     */
    public void handleReturnBook() {
        Document selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            user = borrowService.returnDocument(user, selectedItem);

            user = notificationService.addNotification(user,
                    "Document Returned Successfully",
                    "You have successfully returned the document titled '"
                            + selectedItem.getTitle()
                            + "'."
            );

            setUserInfo();

            alertInfo.setContentText("Return Successfully!");
            alertInfo.show();
        } else {
            alertErr.setContentText("Please choose an item");
            alertErr.show();
        }
    }

    /**
     * click info button.
     */
    public void handleShowBookInfo() throws IOException {
        Document selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }

        String fxmlFile = "/org/example/btl/view/returnedBookInfo-view.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();

        BookInfoController controller = loader.getController();
        controller.setDocument(selectedItem);
        controller.setUser(user);
        controller.setUserReturnBookController(this);
        controller.setBookInfo();

        Stage stage = new Stage();
        stage.setTitle("Document");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
