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

    /**
     * Sets the admin information and updates the document table.
     * This method displays a personalized greeting with the admin's name
     * and populates the document table with all documents retrieved from the document service.
     */
    @Override
    public void setAdminInfo() {
        nameLabel.setText("Hi " + admin.getName());
        documentObservableList = FXCollections.observableArrayList(documentService.findAll());
        tableView.setItems(documentObservableList);
    }

    /**
     * Initializes the controller by setting up the ChoiceBox for criteria selection and
     * configuring the TableView columns to display document properties.
     * This method sets the default criteria value to "Title" and binds the table columns
     * to their respective document properties. Additionally, it formats the authors and genres
     * columns to display a comma-separated list of author and genre names.
     */
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

    /**
     * Handles the admin search action based on the selected criterion (Title, Author, Genre, Publisher).
     * It validates the search keyword, performs the search, and updates the table view with the results.
     * If no results are found, it shows an error message.
     *
     * @param event The action event triggered by the user.
     */
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

    /**
     * Handles the deletion of a document from the table.
     * If the document is currently borrowed, a confirmation alert is shown.
     * If the document is not borrowed, a confirmation alert is shown.
     *
     * The document is deleted from the database, and the table view is refreshed.
     */
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
                }
            });
        }
    }

    /**
     * Handles the update action for a selected document in the table view.
     * If no document is selected, the method returns early.
     * Otherwise, it opens a new scene where the document can be updated.
     * The selected document is passed to the update controller for further processing.
     *
     * @param event The action event triggered by the update action (e.g., button click).
     * @throws IOException If the FXML file for the update view cannot be loaded.
     */
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
