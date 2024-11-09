package org.example.btl.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.btl.model.Admin;
import org.example.btl.model.User;
import org.example.btl.service.AdminService;
import org.example.btl.service.UserService;

import java.io.IOException;
import java.sql.Date;

public class AddMemberController  {
    private UserService userService = new UserService();
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
    private DatePicker birthdayText;
    @FXML
    private ToggleGroup gender;

    public void handleAddMember (ActionEvent event) throws IOException {
        String name = nameText.getText();
        String email = emailText.getText();
        String username = usernameText.getText();
        String password = passwordText.getText();

        RadioButton selectedGender = (RadioButton) gender.getSelectedToggle();
        String gender = selectedGender == null ? "" : selectedGender.getText();

        Date birthday = birthdayText.getValue() == null ? null : Date.valueOf(birthdayText.getValue());

        String validationMess = userService.validateRegistration(name, email, username, password,  gender, birthday);
        if(validationMess != null) {
            alert.setContentText(validationMess);
            alert.show();
        } else {
            User newUser = new User(name, email, username, password, birthday, gender);
            userService.save(newUser);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/btl/view/signUpSuccessScene.fxml"));
            root = loader.load();
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }

    @FXML
    public void switchToAdminHome(ActionEvent event) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/btl/view/adminHome.fxml"));
            root = fxmlLoader.load();
            scene = new Scene(root);
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
    }

    @FXML
    public void switchToAdminSearchBook(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/btl/view/adminSearchBook.fxml"));
        root = fxmlLoader.load();
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void switchToAddBookScene(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/btl/view/adminAddBook.fxml"));
        root = fxmlLoader.load();
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    public void switchToDeleteBookScene(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/btl/view/adminDeleteBook.fxml"));
        root = fxmlLoader.load();
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
