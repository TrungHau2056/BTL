package org.example.btl.controller;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.btl.controller.admincontrollers.AdminHomeController;
import org.example.btl.controller.usercontrollers.UserHomeController;
import org.example.btl.model.Admin;
import org.example.btl.model.User;
import org.example.btl.service.AdminService;
import org.example.btl.service.UserService;

import java.io.IOException;
import java.util.Objects;

public class LoginController {
    private AdminService adminService;
    private UserService userService;
    Alert alertErr = new Alert(Alert.AlertType.ERROR);

    private boolean isProcessing = false;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField usernameText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private TextField showPassText;
    @FXML
    private ToggleGroup role;
    @FXML
    private Button eyeButton;

    /**
     * click eye to show password.
     */
    public void handleShowHiddenPass() {
        if (passwordText.isVisible()) {
            showPassText.setText(passwordText.getText());
            passwordText.setVisible(false);
            showPassText.setVisible(true);

            eyeButton.getStyleClass().remove("eye-button");
            eyeButton.getStyleClass().add("eye-button-hidden");
        } else {
            passwordText.setText(showPassText.getText());
            showPassText.setVisible(false);
            passwordText.setVisible(true);

            eyeButton.getStyleClass().remove("eye-button-hidden");
            eyeButton.getStyleClass().add("eye-button");
        }
    }

    /**
     * click sign up link to switch sign up scene.
     *
     * @param event the action event triggered by the user.
     * @throws IOException if parent cannot load.
     */
    public void switchToSignUp(ActionEvent event) throws IOException {
        if (isProcessing) {
            alertErr.setContentText("Please wait");
            alertErr.show();
            return;
        }

        String fxmlFile = "/org/example/btl/view/signUp-view.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        root = loader.load();

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * click login button.
     *
     * @param event the action event triggered by the user.
     */
    public void handleLogin(ActionEvent event) {
        if (isProcessing) {
            alertErr.setContentText("Please wait");
            alertErr.show();
            return;
        }

        isProcessing = true;
        String username = usernameText.getText();
        String password = passwordText.isVisible() ? passwordText.getText() : showPassText.getText();

        RadioButton selectedRole = (RadioButton) role.getSelectedToggle();
        String role = selectedRole == null ? "" : selectedRole.getText();

        if (Objects.equals(username, "")
                || Objects.equals(password, "")
                || Objects.equals(role, "")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter all the information!");
            alert.show();
            isProcessing = false;
        } else {
            if (role.equals("User")) {
                userService = new UserService();
                Task<User> userLoginTask = new Task<>() {
                    @Override
                    protected User call() {
                        return userService.findByPassAndUsername(username, password);
                    }
                };

                userLoginTask.setOnSucceeded(e -> {
                    User user = userLoginTask.getValue();
                    if (user == null) {
                        alertErr.setContentText("Wrong login information! Please try again");
                        alertErr.show();
                        isProcessing = false;
                    } else {
                        String fxmlFile = "/org/example/btl/view/userview/userHome-view.fxml";
                        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                        try {
                            root = loader.load();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        UserHomeController controller = loader.getController();
                        controller.setUser(user);
                        controller.setUserInfo();

                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }
                });

                userLoginTask.setOnFailed(e -> {
                    Throwable exception = userLoginTask.getException();
                    if (exception != null) {
                        exception.printStackTrace();
                    }
                    isProcessing = false;
                });

                new Thread(userLoginTask).start();
            } else {
                adminService = new AdminService();
                Task<Admin> adminLoginTask = new Task<>() {
                    @Override
                    protected Admin call() {
                        return adminService.findByPassAndUsername(username, password);
                    }
                };

                adminLoginTask.setOnSucceeded(e -> {
                    Admin admin = adminLoginTask.getValue();
                    if (admin == null) {
                        alertErr.setContentText("Wrong login information! Please try again");
                        alertErr.show();
                        isProcessing = false;
                    } else {
                        String fxmlFile = "/org/example/btl/view/adminview/adminHome-view.fxml";
                        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                        try {
                            root = loader.load();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        AdminHomeController controller = loader.getController();
                        controller.setAdmin(admin);
                        controller.setAdminInfo();

                        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }
                });

                adminLoginTask.setOnFailed(e -> {
                    Throwable exception = adminLoginTask.getException();
                    if (exception != null) {
                        exception.printStackTrace();
                    }
                    isProcessing = false;
                });

                new Thread(adminLoginTask).start();
            }
        }
    }
}
