package org.example.btl.controller.usercontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.example.btl.model.User;
import org.example.btl.service.DocumentService;
import org.example.btl.service.UserService;

import java.io.IOException;

public abstract class UserBaseController {
    protected User user;

    protected UserService userService = new UserService();
    protected DocumentService documentService = new DocumentService();

    protected Alert alertErr = new Alert(Alert.AlertType.ERROR);
    protected Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
    protected Alert alertComfirm = new Alert(Alert.AlertType.CONFIRMATION);

    private Stage stage;
    private Scene scene;
    private Parent root;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public abstract void setUserInfo();

    public void switchScene(ActionEvent event, String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        root = loader.load();
        UserBaseController controller = loader.getController();
        controller.setUser(user);
        controller.setUserInfo();

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToUserHomeScreen(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/userview/userHome-view.fxml");
    }

    public void switchToUserSearchBook(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/userview/userSearchBook-view.fxml");
    }

    public void switchToUserBorrowBook(ActionEvent event) throws IOException {

    }

    public void switchToUserReturnBook(ActionEvent event) throws IOException {

    }

    public void switchToUserInfo(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/userview/userInfor-view.fxml");
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
}
