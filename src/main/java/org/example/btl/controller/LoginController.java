package org.example.btl.controller;

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
    Alert alert = new Alert(Alert.AlertType.ERROR);

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField usernameText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private ToggleGroup role;

    public void switchToSignUp(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/btl/view/signUp-view.fxml"));
        root = loader.load();
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void handleLogin(ActionEvent event) throws IOException {
        String username = usernameText.getText();
        String password = passwordText.getText();

        RadioButton selectedRole = (RadioButton) role.getSelectedToggle();
        String role = selectedRole == null ? "" : selectedRole.getText();

        if (Objects.equals(username, "")
                || Objects.equals(password, "")
                || Objects.equals(role, "")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter all the information!");
            alert.show();
        } else {
            if (role.equals("User")) {
                userService = new UserService();
                User user = userService.findByPassAndUsername(username, password);
                if (user == null) {
                    alert.setContentText("Wrong login information! Please try again");
                    alert.show();
                }
                else {
                    //change to user scene
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/btl/view/userview/userHome-view.fxml"));
                    root = loader.load();
                    UserHomeController controller = loader.getController();
                    controller.setUser(user);
                    controller.setUserInfo();

                    stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            } else {
                adminService = new AdminService();
                Admin admin = adminService.findByPassAndUsername(username, password);
                if (admin == null) {
                    alert.setContentText("Wrong login information! Please try again");
                    alert.show();
                } else {
                    //switch to admin scene
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/btl/view/adminview/adminHome-view.fxml"));
                    root = loader.load();
                    AdminHomeController controller = loader.getController();
                    controller.setAdmin(admin);

                    stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            }
        }
    }
}
