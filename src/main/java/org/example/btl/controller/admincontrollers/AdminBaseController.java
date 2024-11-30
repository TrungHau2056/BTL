package org.example.btl.controller.admincontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.example.btl.model.Admin;
import org.example.btl.service.AdminService;
import org.example.btl.service.DocumentService;
import org.example.btl.service.NotificationService;
import org.example.btl.service.UserService;

import java.io.IOException;

public abstract class AdminBaseController {
    protected Admin admin;

    protected UserService userService = new UserService();
    protected DocumentService documentService = new DocumentService();
    protected NotificationService notificationService = new NotificationService();

    protected Alert alertErr = new Alert(Alert.AlertType.ERROR);
    protected Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
    protected Alert alertComfirm = new Alert(Alert.AlertType.CONFIRMATION);

    protected Stage stage;
    protected Parent root;
    protected Scene scene;

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public abstract void setAdminInfo();

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

    public void switchToAdminHome(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/adminview/adminHome-view.fxml");
    }

    public void switchToAdminSearchBook(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/adminview/adminSearchBook-view.fxml");
    }

    public void switchToAddMemberScene(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/adminview/addMember-view.fxml");
    }

    public void switchToAddBookScene(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/adminview/adminAddBook-view.fxml");
    }

    public void switchToUpdateBookScene() {
        alertInfo.setContentText("Please choose document to update in search function first!");
        alertInfo.show();
    }

    public void switchToISBNScene(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/adminview/addByISBN-view.fxml");
    }

    public void switchToShowUserScene(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/adminview/adminShowUser-view.fxml");
    }

    public void switchToAddedDocHistory(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/adminview/historyAddBook-view.fxml");
    }


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

    public void switchChangePasswordScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/btl/view/adminview/adminChangePassword-view.fxml"));
        root = loader.load();

        stage = new Stage();
        scene = new Scene(root, 400, 200);
        stage.setScene(scene);
        stage.show();
    }
}
