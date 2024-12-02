package org.example.btl.controller.admincontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.btl.model.User;

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
        nameLabel.setText("Hi " + admin.getName());
    }

    public void handleAddMember () {
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

            alertInfo.setContentText("User successfully registered!");
            alertInfo.show();
        }
    }
}
