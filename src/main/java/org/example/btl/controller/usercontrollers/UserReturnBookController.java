package org.example.btl.controller.usercontrollers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.btl.controller.BookInfoController;
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
    private Label nameUser;
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
            return new SimpleObjectProperty<>(borrow.getBorrowDate());
        });
    }

    @Override
    public void setUserInfo() {
        nameUser.setText(user.getName());

        List<Document> documents = documentService.findCurrentBorrow(user);
        documentObservableList = FXCollections.observableArrayList(documents);
        tableView.setItems(documentObservableList);
    }

    public void refresh() {
        tableView.refresh();
    }

    public void handleSearchBook(ActionEvent event) {
        String keyword = searchText.getText();
        String criterion = criteria.getValue();
        String status = "Borrowed";

        String validateMessage = documentService.validateSearchByKeyword(keyword);
        if (validateMessage != null) {
            alertErr.setContentText(validateMessage);
            alertErr.show();
        } else {
            List<Document> documents = null;
            switch (criterion) {
                case "Title":
                    documents = documentService.searchByTitle(keyword, user, status);
                    break;
                case "Author":
                    documents = documentService.searchByAuthor(keyword, user, status);
                    break;
                case "Genre":
                    documents = documentService.searchByGenre(keyword, user, status);
                    break;
                case "Publisher":
                    documents = documentService.searchByPublisher(keyword, user, status);
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

    public void handleReturnBook(ActionEvent event) {
        Document selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            borrowService.returnDocument(user, selectedItem);
            setUserInfo();
            refresh();
            alertInfo.setContentText("Return Successfully!");
            alertInfo.show();
        } else {
            alertErr.setContentText("Please choose an item");
            alertErr.show();
        }
    }

    public void handleShowBookInfo(ActionEvent event) {
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
    private void showBookInfoView(Document selectedItem) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/btl/view/returnedBookInfo-view.fxml"));
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

    public void switchToHistoryScene(ActionEvent event) {

    }

}
