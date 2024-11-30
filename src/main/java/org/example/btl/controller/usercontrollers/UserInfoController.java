package org.example.btl.controller.usercontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;

public class UserInfoController extends UserBaseController {
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

    /**
     * set user info.
     */

    @Override
    public void setUserInfo() {
        nameLabel.setText(user.getName());
        byte[] avatarData = user.getAvatar();
        if (avatarData != null) {
            InputStream inputStream = new ByteArrayInputStream(avatarData);
            avatar.setImage(new Image(inputStream));
        }

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

    /**
     * button update.
     * @param event
     * @throws IOException
     */

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
            alertErr.setContentText(validationMess);
            alertErr.show();
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
            alertInfo.setContentText("Your information is successfully updated");
            alertInfo.show();
        }
    }

    /**
     * particular button change.
     * @param event
     * @throws IOException
     */

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
}
