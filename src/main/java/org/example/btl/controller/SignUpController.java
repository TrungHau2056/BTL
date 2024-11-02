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
import org.example.btl.service.AdminService;

import java.io.IOException;
import java.sql.Date;
import java.util.Objects;

public class SignUpController {
    private AdminService adminService = new AdminService();
    private Alert alert = new Alert(Alert.AlertType.ERROR);

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField nameText;
    @FXML
    private TextField emailText;
    @FXML
    private TextField usernameText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private PasswordField confirmedPasswordText;
    @FXML
    private DatePicker birthdayText;
    @FXML
    private ToggleGroup gender;


    public void switchToLogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/btl/view/loginScene.fxml"));
        root = loader.load();
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void handleSignUp(ActionEvent event) throws IOException {
        String name = nameText.getText();
        String email = emailText.getText();
        String username = usernameText.getText();
        String password = passwordText.getText();
        String confirmedPassword = confirmedPasswordText.getText();

        RadioButton selectedGender = (RadioButton) gender.getSelectedToggle();
        String gender = selectedGender == null ? "" : selectedGender.getText();

        Date birthday = birthdayText.getValue() == null ? null : Date.valueOf(birthdayText.getValue());

        if (Objects.equals(name, "")
            || Objects.equals(email, "")
            || Objects.equals(username, "")
            || Objects.equals(password, "")
            || Objects.equals(confirmedPassword, "")
            || Objects.equals(gender, "")
            || Objects.equals(birthday, null)) {
            alert.setContentText("Please enter all the information!");
            alert.show();
        } else if (!Objects.equals(password, confirmedPassword)) {
            alert.setContentText("Passwords do not match. Please try again.");
            alert.show();
        } else if (username.length() < 6) {
            alert.setContentText("Your username must be at least 6 characters long.");
            alert.show();
        } else if (password.length() < 8) {
            alert.setContentText("Your Password must be at least 8 characters long.");
            alert.show();
        } else {
            Admin admin = adminService.findByUsername(username);

            if (admin != null) {
                alert.setContentText("This username already exists. Please choose another one.");
                alert.show();
            } else {
                Admin newAdmin = new Admin(name, email, username, password, birthday, gender);
                adminService.save(newAdmin);

                //change scene
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/btl/view/signUpSuccessScene.fxml"));
                root = loader.load();
                stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
    }
}
