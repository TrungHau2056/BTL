package org.example.btl.controller.admincontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.btl.model.User;

import java.io.IOException;
import java.sql.Date;

public class AddMemberController extends AdminBaseController {
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

    @Override
    public void setAdminInfo() {

    }

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
            alertErr.setContentText(validationMess);
            alertErr.show();
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
}
