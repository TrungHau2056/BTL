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
import org.example.btl.model.Admin;
import org.example.btl.service.AdminService;

import java.io.IOException;
import java.sql.Date;

public class SignUpController {
    private AdminService adminService = new AdminService();
    private Alert alertErr = new Alert(Alert.AlertType.ERROR);

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/btl/view/login-view.fxml"));
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
        Date birthday = birthdayText.getValue() == null ? null : Date.valueOf(birthdayText.getValue());
        RadioButton selectedGender = (RadioButton) gender.getSelectedToggle();
        String gender = selectedGender == null ? "" : selectedGender.getText();

        Task<String> signUpTask = new Task<>() {
            @Override
            protected String call() throws Exception {
                String validationMess = adminService.validateRegistration(name, email, username, password, confirmedPassword, gender, birthday);
                if (validationMess != null) {
                    return validationMess;
                }
                Admin newAdmin = new Admin(name, email, username, password, birthday, gender);
                adminService.save(newAdmin);
                return null;
            }
        };

        signUpTask.setOnSucceeded(e -> {
            String validationMess = signUpTask.getValue();
            if (validationMess != null) {
                alertErr.setContentText(validationMess);
                alertErr.show();
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/btl/view/signUpSuccess-view.fxml"));
                    try {
                        root = loader.load();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        });

        signUpTask.setOnFailed(e -> {
            alertErr.setContentText("Error: " + signUpTask.getException().getMessage());
            alertErr.show();
        });

        new Thread(signUpTask).start();
    }
}
