package org.example.btl.controller.admincontrollers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.btl.model.Author;
import org.example.btl.model.Document;
import org.example.btl.model.Genre;
import org.example.btl.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AdminSearchBookController extends AdminBaseController {
    @FXML
    private TextField searchText;

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
    private TableColumn<Document, String> publisherCol;
    @FXML
    private TableColumn<Document, String> descriptionCol;
    @FXML
    private TableColumn<Document, Integer> quantityCol;

    private ObservableList<Document> documentObservableList;

    @Override
    public void setAdminInfo() {
        documentObservableList = FXCollections.observableArrayList(documentService.findAll());
        tableView.setItems(documentObservableList);
    }

    @FXML
    public void initialize() {
        // Choice Box
        criteria.getItems().addAll("Title", "Author", "Genre", "Publisher");
        criteria.setValue("Title");

        // Table view
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

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
                data.getValue().getPublisher() != null ? data.getValue().getPublisher().getName() : "Not available"));
    }

    public void refresh() {
        tableView.refresh();
    }

    public void handleAdminSearch(ActionEvent event) {
        String keyword = searchText.getText();
        String criterion = criteria.getValue();

        String validateMessage = documentService.validateSearchByKeyword(keyword);
        if (validateMessage != null) {
            alertErr.setContentText(keyword);
            alertErr.show();
        } else {
            String status = "All";

            Task<List<Document>> searchDocTask = new Task<>() {
                @Override
                protected List<Document> call() {
                    switch (criterion) {
                        case "Title":
                            return documentService.searchByTitle(keyword, null, status);
                        case "Author":
                            return documentService.searchByAuthor(keyword, null, status);
                        case "Genre":
                            return documentService.searchByGenre(keyword, null, status);
                        case "Publisher":
                            return documentService.searchByPublisher(keyword, null, status);
                    }
                    return null;
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

    public void handleDelete() {
        Document document = tableView.getSelectionModel().getSelectedItem();
        if (document == null) {
            return;
        }

        if (documentService.isCurrentlyBorrow(document)) {
            alertComfirm.setTitle("Delete comfirmation");
            alertComfirm.setHeaderText("Delete document comfirmation");
            alertComfirm.setContentText("Someone is borrowing this document!" +
                    " Do you still want to delete and send them a notification?");

            alertComfirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    List<User> users = documentService.findUserCurrentlyBorrow(document);
                    for (User user : users) {
                        notificationService.addNotification(user, "Document Deleted",
                                "The document you borrowed titled '" + document.getTitle() + "' has been deleted.");
                    }

                    documentService.deleteDocument(document);
                    setAdminInfo();
                    refresh();
                }
            });
        } else {
            alertComfirm.setTitle("Delete comfirmation");
            alertComfirm.setHeaderText("Delete document comfirmation");
            alertComfirm.setContentText("Are you sure?" +
                    " All information about this document will be deleted from the database.");

            alertComfirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    documentService.deleteDocument(document);
                    setAdminInfo();
                    refresh();
                }
            });
        }
    }

    public void handleUpdate(ActionEvent event) throws IOException {
        Document document = tableView.getSelectionModel().getSelectedItem();
        if (document == null) {
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/btl/view/adminview/adminUpdateBook-view.fxml"));
        root = loader.load();
        AdminUpdateBookController controller = loader.getController();
        controller.setAdmin(admin);
        controller.setDocument(document);
        controller.setAdminInfo();

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
