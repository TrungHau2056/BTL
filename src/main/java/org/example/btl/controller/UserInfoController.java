package org.example.btl.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.btl.model.User;
import org.example.btl.service.UserService;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class UserInfoController implements Initializable {
    private User user;

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
    @FXML
    private RadioButton maleRadioButton;
    @FXML
    private RadioButton femaleRadioButton;
    @FXML
    private RadioButton otherRadioButton;
    @FXML
    private Button updateButton;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setUserInfo() {
        nameText.setText(user.getName());
        emailText.setText(user.getEmail());
        usernameText.setText(user.getUsername());
        passwordText.setText(user.getPassword());
        birthdayText.setValue(user.getBirthday().toLocalDate());
        switch(user.getGender()) {
            case "Male":
                gender.selectToggle(maleRadioButton);
                break;
            case "Female":
                gender.selectToggle(femaleRadioButton);
                break;
            case "Other":
                gender.selectToggle(otherRadioButton);
                break;
        }
    }

    // button update
    public void handleUpdateUser(ActionEvent event) throws IOException {
        String name = nameText.getText();
        String email = emailText.getText();
        String username = usernameText.getText();
        String password = passwordText.getText();
        Date birthday = birthdayText.getValue() == null ? null : Date.valueOf(birthdayText.getValue());
        RadioButton selectedGender = (RadioButton) gender.getSelectedToggle();
        String gender = selectedGender == null ? "" : selectedGender.getText();

        String validationMess = userService.validateUpdate(name, email, username, user.getUsername(), password,  gender, birthday);
        if(validationMess != null) {
            alert.setContentText(validationMess);
            alert.show();
        } else {
            user.setName(name);
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(password);
            user.setBirthday(birthday);
            user.setGender(gender);

            userService.update(user);

            nameText.setDisable(true);
            emailText.setDisable(true);
            usernameText.setDisable(true);
            passwordText.setDisable(true);
            birthdayText.setDisable(true);
            updateButton.setDisable(true);

            //update successful notification
        }
    }

    // pen that change user's name
    public void handleUpdateName(ActionEvent event) throws IOException {
        nameText.setDisable(false);
        updateButton.setDisable(false);
    }

    // pen that change user's email
    public void handleUpdateEmail(ActionEvent event) throws IOException {
        emailText.setDisable(false);
        updateButton.setDisable(false);
    }

    // pen that change user's username
    public void handleUpdateUsername (ActionEvent event) throws IOException {
        usernameText.setDisable(false);
        updateButton.setDisable(false);
    }

    // pen that change user's password
    public void handleUpdatePassWord (ActionEvent event) throws IOException {
        passwordText.setDisable(false);
        updateButton.setDisable(false);
    }

    // pen that change user's birthday
    public void handleUpdateBirthday (ActionEvent event) throws IOException {
        birthdayText.setDisable(false);
        updateButton.setDisable(false);
    }

    // to change gender
    public void handleUpdateGender (ActionEvent event) throws IOException {
        maleRadioButton.setDisable(false);
        femaleRadioButton.setDisable(false);
        otherRadioButton.setDisable(false);
        updateButton.setDisable(false);
    }

    // button to switch to user home
    public void switchToUserHomeScreen(ActionEvent event) throws IOException {

    }

    // button to switch to searchbook scene
    public void switchToUserSearchBook(ActionEvent event) throws IOException {

    }

    // button to switch to borrowbook scene
    public void switchToUserBorrowBook(ActionEvent event) throws IOException {

    }

    // button to switch to returnbook scene
    public void switchToUserReturnBook(ActionEvent event) throws IOException {

    }

    // button logout
    public void handleLogOut(ActionEvent event) throws IOException {

    }



}
