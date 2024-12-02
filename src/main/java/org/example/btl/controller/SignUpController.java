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
    private TextField showPassText;
    @FXML
    private PasswordField confirmedPasswordText;
    @FXML
    private DatePicker birthdayText;
    @FXML
    private ToggleGroup gender;

    @FXML
    private Button eyeButton;

    /**
     * click eye button.
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
     * switch to log in scene.
     * @param event the action event triggered by the user.
     * @throws IOException if parent cannot load.
     */

    public void switchToLogin(ActionEvent event) throws IOException {
        String fxmlFile = "/org/example/btl/view/login-view.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        root = loader.load();

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * click sign up button.
     * @param event the action event triggered by the user.
     */

    public void handleSignUp(ActionEvent event) {
        String name = nameText.getText();
        String email = emailText.getText();
        String username = usernameText.getText();
        String password = passwordText.isVisible() ? passwordText.getText() : showPassText.getText();
        String confirmedPassword = confirmedPasswordText.getText();
        Date birthday = birthdayText.getValue() == null ? null : Date.valueOf(birthdayText.getValue());
        RadioButton selectedGender = (RadioButton) gender.getSelectedToggle();
        String gender = selectedGender == null ? "" : selectedGender.getText();

        Task<String> signUpTask = new Task<>() {
            @Override
            protected String call() {
                String validationMess = adminService.validateRegistration(name,
                        email,
                        username,
                        password,
                        confirmedPassword,
                        gender,
                        birthday
                );
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
                String fxmlFile = "/org/example/btl/view/signUpSuccess-view.fxml";
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
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
            Throwable exception = signUpTask.getException();
            if (exception != null) {
                exception.printStackTrace();
            }
        });

        new Thread(signUpTask).start();
    }
}
