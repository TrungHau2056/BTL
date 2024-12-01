package org.example.btl.controller.usercontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.btl.model.User;
import org.example.btl.service.BorrowService;
import org.example.btl.service.DocumentService;
import org.example.btl.service.NotificationService;
import org.example.btl.service.UserService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public abstract class UserBaseController {
    protected User user;

    protected UserService userService = new UserService();
    protected DocumentService documentService = new DocumentService();
    protected BorrowService borrowService = new BorrowService();
    protected NotificationService notificationService = new NotificationService();

    protected Alert alertErr = new Alert(Alert.AlertType.ERROR);
    protected Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
    protected Alert alertComfirm = new Alert(Alert.AlertType.CONFIRMATION);

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    protected ImageView avatar;
    @FXML
    protected ImageView avatarInfo;
    @FXML
    protected Label nameLabel;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * set info for userScenes.
     */

    public abstract void setUserInfo();

    /**
     * general switchScene.
     * @param event the action event triggered by the user.
     * @param path the path to the FXML file of the new scene.
     * @throws IOException if the FXML file cannot be loaded.
     */

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

    /**
     * switch particular scenes.
     * @param event the action event triggered by the user.
     * @throws IOException if the FXML file cannot be loaded.
     */

    public void switchToUserHomeScreen(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/userview/userHome-view.fxml");
    }

    public void switchToUserSearchBook(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/userview/userSearchBook-view.fxml");
    }

    public void switchToBorrowHistoryScene(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/userview/userBorrowHistory-view.fxml");
    }

    public void switchToUserReturnBook(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/userview/userReturnBook-view.fxml");
    }

    public void switchToUserReturnHistory(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/userview/userReturnHistory-view.fxml");
    }

    public void switchToUserInfo(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/userview/userInfor-view.fxml");
    }

    public void switchToNotificationView(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/userview/userNotification-view.fxml");
    }

    /**
     * button change avatar.
     * @param event the action event triggered by the user.
     * @throws IOException if the FXML file cannot be loaded.
     */

    public void handleChangeAvatar(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.bmp"));

        File selectedFile = fileChooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());

        if (selectedFile != null) {
            Image selectedImage = new Image(selectedFile.toURI().toString());
            avatar.setImage(selectedImage);
            avatarInfo.setImage(selectedImage);

            byte[] avatarData = Files.readAllBytes(selectedFile.toPath());
            user.setAvatar(avatarData);
            userService.update(user);
        }
    }

    /**
     * button logout.
     * @param event the action event triggered by the user.
     */

    public void handleLogOut(ActionEvent event) {
        alertComfirm.setTitle("Log out comfirmation");
        alertComfirm.setHeaderText("Log out comfirmation");
        alertComfirm.setContentText("Are you sure?");
        alertComfirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                String fxmlFile = "/org/example/btl/view/login-view.fxml";
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
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
