package org.example.btl.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserInfoController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    // show user's information
    // đang set ko thể thay đổi
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
    private Button updateButton;


    // pen (những button mà khi ấn cho phép thay ôổi thông tin ng dùng)
    // ko biết có duùng đến những id này ko nma cứ thêm vào nhỡ l Hair dùng
    @FXML
    private Button updateName;
    @FXML
    private Button updateEmail;
    @FXML
    private Button updateUsername;
    @FXML
    private Button updatePassword;
    @FXML
    private Button updateBirthday;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    // button update
    public void handleUpdateUser(ActionEvent event) throws IOException {

    }

    // pen that change user's name
    public void handleUpdateName(ActionEvent event) throws IOException {

    }

    // pen that change user's email
    public void handleUpdateEmail(ActionEvent event) throws IOException {

    }

    // pen that change user's username
    public void handleUpdateUsername (ActionEvent event) throws IOException {

    }

    // pen that change user's password
    public void handleUpdatePassWord (ActionEvent event) throws IOException {

    }

    // pen that change user's birthday
    public void handleUpdateBirthday (ActionEvent event) throws IOException {

    }

    // to change gender
    public void handleUpdateGender (ActionEvent event) throws IOException {

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
