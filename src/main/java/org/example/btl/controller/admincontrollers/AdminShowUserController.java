package org.example.btl.controller.admincontrollers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.btl.model.Document;
import org.example.btl.model.User;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class AdminShowUserController extends AdminBaseController implements Initializable {
    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User, Integer> idCol;
    @FXML
    private TableColumn<User, String> nameCol;
    @FXML
    private TableColumn<User, String> emailCol;
    @FXML
    private TableColumn<User, String> usernameCol;
    @FXML
    private TableColumn<User, String> passwordCol;
    @FXML
    private TableColumn<User, Date> birthdayCol;
    @FXML
    private TableColumn<User, String> genderCol;

    private ObservableList<User> userObservableList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        birthdayCol.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
    }

    @Override
    public void setAdminInfo() {
        userObservableList = FXCollections.observableArrayList(userService.findAll());
        tableView.setItems(userObservableList);
    }

    public void refresh() {
        tableView.refresh();
    }

    public void handleDeleteUser() {
        User user = tableView.getSelectionModel().getSelectedItem();
        if (user == null) {
            return;
        }

        alertComfirm.setTitle("Delete user");
        alertComfirm.setHeaderText("Delete user comfirmation");
        alertComfirm.setContentText("Are you sure?" +
                " All information about this user will be deleted from the database.");

        alertComfirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                userService.deleteUser(user);
                setAdminInfo();
                refresh();
            }
        });
    }

    public void handleSendNotification() {
        User user = tableView.getSelectionModel().getSelectedItem();
        if (user == null) {
            alertErr.setContentText("Please choose an user!");
            return;
        }

        //
    }
}
