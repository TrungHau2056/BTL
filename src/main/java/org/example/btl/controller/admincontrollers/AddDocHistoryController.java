package org.example.btl.controller.admincontrollers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.btl.model.Author;
import org.example.btl.model.Borrow;
import org.example.btl.model.Document;
import org.example.btl.model.Genre;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class AddDocHistoryController extends AdminBaseController implements Initializable {
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
    private TableColumn<Document, Date> addedDateCol;

    private ObservableList<Document> documentObservableList;

    /**
     * Sets the admin's display information.
     * Updates the admin's name and the list of documents added by the admin.
     */
    @Override
    public void setAdminInfo() {
        nameLabel.setText("Hi " + admin.getName());
        documentObservableList = FXCollections.observableArrayList(documentService.findDocAddedByAdmin(admin));
        tableView.setItems(documentObservableList);
    }

    /**
     * Initializes the table view with column data.
     * Sets up cell value factories for each column to map document properties.
     *
     * @param url the location used to resolve relative paths, or null if not available.
     *
     * @param resourceBundle the resources used to localize the UI.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        addedDateCol.setCellValueFactory(new PropertyValueFactory<>("addedDate"));

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
     * Handles the update action triggered by the user.
     * Opens the update book view and passes the selected document and admin information to the controller.
     *
     * @param event the action event triggered by the update button
     * @throws IOException if an error occurs while loading the update view
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
