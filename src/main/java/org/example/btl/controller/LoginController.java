package org.example.btl.controller;

import jakarta.persistence.Query;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.btl.model.Admin;
import org.example.btl.model.HibernateUtils;
import org.example.btl.model.User;
import org.example.btl.service.AdminService;
import org.example.btl.service.UserService;
import org.hibernate.Session;

import java.io.IOException;
import java.util.List;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/btl/view/signUpScene.fxml"));
        root = loader.load();
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void handleLogin(ActionEvent event) {
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
                }
            } else {
                adminService = new AdminService();
                Admin admin = adminService.findByPassAndUsername(username, password);
                if (admin == null) {
                    alert.setContentText("Wrong login information! Please try again");
                    alert.show();
                } else {
                    //change to admin scene
                }
            }
        }
    }
}
