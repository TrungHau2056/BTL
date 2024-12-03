package org.example.btl.controller.admincontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.btl.model.Admin;
import org.example.btl.service.AdminService;
import org.example.btl.service.DocumentService;
import org.example.btl.service.NotificationService;
import org.example.btl.service.UserService;

import java.io.IOException;

public abstract class AdminBaseController {
    protected Admin admin;

    protected AdminService adminService = new AdminService();
    protected UserService userService = new UserService();
    protected DocumentService documentService = new DocumentService();
    protected NotificationService notificationService = new NotificationService();

    protected Alert alertErr = new Alert(Alert.AlertType.ERROR);
    protected Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
    protected Alert alertComfirm = new Alert(Alert.AlertType.CONFIRMATION);

    protected Stage stage;
    protected Parent root;
    protected Scene scene;

    @FXML
    protected Label nameLabel;

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public abstract void setAdminInfo();

    /**
     * Switches the current scene to a new scene specified by the given path.
     * Loads the new scene, sets the admin information, and displays it.
     *
     * @param event The event that triggers the scene switch.
     * @param path The path to the FXML file of the new scene.
     * @throws IOException If there is an error loading the new scene.
     */
    public void switchScene(ActionEvent event, String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        root = loader.load();
        AdminBaseController controller = loader.getController();
        controller.setAdmin(admin);
        controller.setAdminInfo();

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Switches the current scene to the admin home scene.
     * Calls the `switchScene` method with the appropriate path for the admin home view.
     *
     * @param event The event that triggers the scene switch.
     * @throws IOException If there is an error loading the admin home scene.
     */
    public void switchToAdminHome(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/adminview/adminHome-view.fxml");
    }

    /**
     * Switches the current scene to the admin search book scene.
     * Calls the `switchScene` method with the appropriate path for the admin search book view.
     *
     * @param event The event that triggers the scene switch.
     * @throws IOException If there is an error loading the admin search book scene.
     */
    public void switchToAdminSearchBook(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/adminview/adminSearchBook-view.fxml");
    }

    /**
     * Switches the current scene to the add member scene.
     * Calls the `switchScene` method with the appropriate path for the add member view.
     *
     * @param event The event that triggers the scene switch.
     * @throws IOException If there is an error loading the add member scene.
     */
    public void switchToAddMemberScene(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/adminview/addMember-view.fxml");
    }

    /**
     * Switches the current scene to the add book scene.
     * Calls the `switchScene` method with the appropriate path for the add book view.
     *
     * @param event The event that triggers the scene switch.
     * @throws IOException If there is an error loading the add book scene.
     */
    public void switchToAddBookScene(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/adminview/adminAddBook-view.fxml");
    }

    /**
     * Displays an alert informing the user to choose a document to update via the search function.
     * This method is called when the user attempts to switch to the update book scene without selecting a document.
     */
    public void switchToUpdateBookScene() {
        alertInfo.setContentText("Please choose document to update in search function first!");
        alertInfo.show();
    }

    /**
     * Switches the current scene to the add by ISBN scene.
     * Calls the `switchScene` method with the appropriate path for the add by ISBN view.
     *
     * @param event The event that triggers the scene switch.
     * @throws IOException If there is an error loading the add by ISBN scene.
     */
    public void switchToISBNScene(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/adminview/addByISBN-view.fxml");
    }

    /**
     * Switches the current scene to the show user scene.
     * Calls the `switchScene` method with the appropriate path for the show user view.
     *
     * @param event The event that triggers the scene switch.
     * @throws IOException If there is an error loading the show user scene.
     */
    public void switchToShowUserScene(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/adminview/adminShowUser-view.fxml");
    }

    /**
     * Switches the current scene to the added document history scene.
     * Calls the `switchScene` method with the appropriate path for the added document history view.
     *
     * @param event The event that triggers the scene switch.
     * @throws IOException If there is an error loading the added document history scene.
     */
    public void switchToAddedDocHistory(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/adminview/historyAddBook-view.fxml");
    }

    /**
     * Handles the log out process by displaying a confirmation dialog.
     * If the user confirms the log out, it switches to the login scene.
     *
     * @param event The event that triggers the log out process.
     */
    public void handleLogOut(ActionEvent event) {
        alertComfirm.setTitle("Log out comfirmation");
        alertComfirm.setHeaderText("Log out comfirmation");
        alertComfirm.setContentText("Are you sure?");

        alertComfirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/btl/view/login-view.fxml"));
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        });
    }
}
