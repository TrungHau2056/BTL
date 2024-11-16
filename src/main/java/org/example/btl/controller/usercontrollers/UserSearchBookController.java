package org.example.btl.controller.usercontrollers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.btl.controller.BookInfoController;
import org.example.btl.model.Author;
import org.example.btl.model.Borrow;
import org.example.btl.model.Document;
import org.example.btl.model.Genre;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class UserSearchBookController extends UserBaseController implements Initializable {
    @FXML
    private TextField titleSearchText;

    @FXML
    private ChoiceBox<String> TypeBook;

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
    private TableColumn<Document, String> descriptionCol;
    @FXML
    private TableColumn<Document, String> statusCol;

    private ObservableList<Document> documentObservableList;

    private Stage stage;
    private Scene scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
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

        statusCol.setCellValueFactory(data -> {
            Document document = data.getValue();
            Borrow borrow = borrowService.findByUserAndDocument(user, document);
            String status = borrow == null ? "Borrowed" : "Not Borrowed";
            return new SimpleStringProperty(status);
        });

        stage = (Stage) tableView.getScene().getWindow();
        scene = tableView.getScene();
    }

    // quay lại scene truoc do
    public void handleBackButton(ActionEvent event) {
        stage.setScene(scene);
        stage.show();
    }

    public void handleTableClick() {
        Document selectedItem = tableView.getSelectionModel().getSelectedItem();
        if(selectedItem != null) {
            try {
                showBookInfoView(selectedItem);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showBookInfoView(Document selectedItem) throws  Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/btl/view/bookInfo-view.fxml"));
        Parent root = loader.load();

        BookInfoController controller = loader.getController();
        controller.setDocument(selectedItem);
        controller.setUserSearch(this);
        controller.setBookInfo();


        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    }

    @Override
    public void setUserInfo() {

    }

    public void handleUserSearch(ActionEvent event) {
        String titleKeyword = titleSearchText.getText();
        String validateMessage = documentService.validateSearchByTitle(titleKeyword);
        if (validateMessage != null) {
            alertErr.setContentText(titleKeyword);
            alertErr.show();
        } else {
            List<Document> documents = documentService.searchByTitleKeyword(titleKeyword);
            //
            if(documents.isEmpty()) {
                alertErr.setContentText("No search results match the keyword.");
                alertErr.show();
            } else {
                documentObservableList = FXCollections.observableArrayList(documents);
                tableView.setItems(documentObservableList);
            }
        }
    }
}
