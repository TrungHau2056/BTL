package org.example.btl.controller.usercontrollers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.btl.dao.AdminDAO;
import org.example.btl.model.Author;
import org.example.btl.model.Borrow;
import org.example.btl.model.Document;
import org.example.btl.model.Genre;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;


public class UserReturnBookController extends UserBaseController implements Initializable {
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
    private TableColumn<Document, Date> borrowDateCol;

    private ObservableList<Document> documentObservableList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
            Borrow borrow = borrowService.findByUserAndDocument(user, data.getValue());
            return new SimpleObjectProperty<>(borrow.getBorrowDate());
        });
    }

    @Override
    public void setUserInfo() {

        List<Document> documents = documentService.findCurrentBorrow(user);
        documentObservableList = FXCollections.observableArrayList(documents);
        tableView.setItems(documentObservableList);
    }

    @FXML
    public void handleSearchBook(ActionEvent event) {

    }

    public void handleReturnBook(ActionEvent event) {

    }

    public void handleShowBookInfo(ActionEvent event) {

    }
}
