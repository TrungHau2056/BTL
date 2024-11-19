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
import org.example.btl.model.*;

import java.io.IOException;
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
    private ChoiceBox<String> status;

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

    private Stage stage;
    private Scene scene;

    public User getUser() {
        return super.getUser();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        criteria.getItems().addAll("Title", "Author", "Genre", "Publisher");
        criteria.setValue("Title");

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

        publisherCol.setCellValueFactory(data -> {
            return new SimpleStringProperty(data.getValue().getPublisher().getName());
        });

    }

    @Override
    public void setUserInfo() {
        statusCol.setCellValueFactory(data -> {
            Document document = data.getValue();
            Borrow borrow = borrowService.findByUserAndDocument(user, document);
            String status = (borrow == null ? "Not Borrowed" : "Borrowed");
            return new SimpleStringProperty(status);
        });

    }


    public void handleTableClick() {
        Document selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                showBookInfoView(selectedItem);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No item selected.");
        }
    }


    private void showBookInfoView(Document selectedItem) throws  Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/btl/view/bookInfo-view.fxml"));
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

    public void handleUserSearch(ActionEvent event) {
        String keyword = searchText.getText();
        String criterion = criteria.getValue();

        String validateMessage = documentService.validateSearchByKeyword(keyword);
        if (validateMessage != null) {
            alertErr.setContentText(keyword);
            alertErr.show();
        } else {
            List<Document> documents = null;
            switch (criterion) {
                case "Title":
                    documents = documentService.searchByTitleKeyword(keyword);
                    break;
                case "Author":
                    documents = documentService.searchByAuthorKeyword(keyword);
                    break;
                case "Genre":
                    documents = documentService.searchByGenreKeyword(keyword);
                    break;
                case "Publisher":
                    documents = documentService.searchByPublisherKeyword(keyword);
                    break;
            }

            if (documents.isEmpty()) {
                alertErr.setContentText("No search results match the keyword.");
                alertErr.show();
            } else {
                documentObservableList = FXCollections.observableArrayList(documents);
                tableView.setItems(documentObservableList);
            }
        }

    }
}
