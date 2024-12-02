package org.example.btl.controller.admincontrollers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.btl.model.User;

import java.net.URL;
import java.sql.Date;
import java.util.List;
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

    @FXML
    private TextField titleText;
    @FXML
    private TextArea messageText;

    private ObservableList<User> userObservableList;

    /**
     * Initializes the table columns by setting up their cell value factories.
     * This method is called automatically when the view is loaded.
     * It binds the columns to the properties of the User object.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if not needed.
     */
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

    /**
     * Sets the admin's information and loads the list of users asynchronously.
     * This method updates the name label with the admin's name and populates
     * the user table with a list of users fetched from the user service.
     */
    @Override
    public void setAdminInfo() {
        nameLabel.setText("Hi " + admin.getName());
        Task<List<User>> loadUserTask = new Task<>() {
            @Override
            protected List<User> call() {
                return userService.findAll();
            }
        };

        loadUserTask.setOnSucceeded(e -> {
            userObservableList = FXCollections.observableArrayList(loadUserTask.getValue());
            tableView.setItems(userObservableList);
        });

        loadUserTask.setOnFailed(e -> {
            Throwable exception = loadUserTask.getException();
            if (exception != null) {
                exception.printStackTrace();
            }
        });

        new Thread(loadUserTask).start();
    }

    /**
     * Refreshes the table view by reloading the data displayed.
     * This method is used to update the content of the table view.
     */
    public void refresh() {
        tableView.refresh();
    }

    /**
     * Handles the deletion of a selected user from the table view.
     * This method shows a confirmation dialog asking the admin to confirm the deletion of a user.
     * If confirmed, the selected user will be deleted from the database and the table view will be refreshed.
     * If the deletion fails, an error message will be displayed.
     */
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
                Task<Void> deleteUserTask = new Task<>() {
                    @Override
                    protected Void call() throws Exception {
                        userService.deleteUser(user);
                        return null;
                    }
                };

                deleteUserTask.setOnSucceeded(e -> {
                    setAdminInfo();
                    refresh();
                });

                deleteUserTask.setOnFailed(e -> {
                    alertErr.setContentText(deleteUserTask.getException().getMessage());
                    alertErr.show();
                });

                new Thread(deleteUserTask).run();
            }
        });
    }

    /**
     * Handles the process of sending a notification to a selected user.
     * This method retrieves the selected user from the table view and sends a notification with the provided title and message.
     * If no user is selected, an error message is displayed. After successfully sending the notification,
     * a confirmation message is shown to the admin.
     */
    public void handleSendNotification() {
        User user = tableView.getSelectionModel().getSelectedItem();
        if (user == null) {
            alertErr.setContentText("Please choose an user!");
            return;
        }

        String title = titleText.getText();
        String message = messageText.getText();

        notificationService.addNotification(user, title, message);
        alertInfo.setContentText("Successfully sent a notification to '" + user.getName() + "'.");
        alertInfo.show();
    }
}
