package org.example.btl.controller.admincontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Objects;

public class AdminChangePassController extends AdminBaseController{
    @FXML
    private PasswordField curPassText;
    @FXML
    private TextField showCurPassText;
    @FXML
    private PasswordField newPassText;
    @FXML
    private TextField showNewPassText;
    @FXML
    private PasswordField confirmedPassText;

    @FXML
    private Button curEyeButton;
    @FXML
    private Button newEyeButton;

    private AdminHomeController adminHomeController;

    public void setAdminHomeController(AdminHomeController adminHomeController) {
        this.adminHomeController = adminHomeController;
    }

    public void handleShowHiddenPass() {
        if (curPassText.isVisible()) {
            showCurPassText.setText(curPassText.getText());
            curPassText.setVisible(false);
            showCurPassText.setVisible(true);

            curEyeButton.getStyleClass().remove("eye-button");
            curEyeButton.getStyleClass().add("eye-button-hidden");
        } else {
            curPassText.setText(showCurPassText.getText());
            showCurPassText.setVisible(false);
            curPassText.setVisible(true);

            curEyeButton.getStyleClass().remove("eye-button-hidden");
            curEyeButton.getStyleClass().add("eye-button");
        }
    }

    public void handleShowNewPass() {
        if (newPassText.isVisible()) {
            showNewPassText.setText(newPassText.getText());
            newPassText.setVisible(false);
            showNewPassText.setVisible(true);

            newEyeButton.getStyleClass().remove("eye-button");
            newEyeButton.getStyleClass().add("eye-button-hidden");
        } else {
            newPassText.setText(showNewPassText.getText());
            showNewPassText.setVisible(false);
            newPassText.setVisible(true);

            newEyeButton.getStyleClass().remove("eye-button-hidden");
            newEyeButton.getStyleClass().add("eye-button");
        }
    }

    @Override
    public void setAdminInfo() {

    }

    public void handleConfirm(ActionEvent event) {
        String curPass = curPassText.isVisible() ? curPassText.getText() : showCurPassText.getText();
        String newPass = newPassText.isVisible() ? newPassText.getText() : showNewPassText.getText();
        String confirmedPass = confirmedPassText.getText();

        if (!Objects.equals(curPass, admin.getPassword())) {
            alertErr.setContentText("Wrong current password!");
            alertErr.show();
        } else if (Objects.equals(newPass, "")) {
            alertErr.setContentText("Please enter your new password.");
            alertErr.show();
        } else if (!Objects.equals(newPass, confirmedPass)) {
            alertErr.setContentText("Passwords do not match. Please try again.");
            alertErr.show();
        } else {
            admin.setPassword(newPass);
            admin = adminService.updateAdmin(admin);
            adminHomeController.setAdmin(admin);

            alertInfo.setContentText("Password successfully changed");

            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
        }
    }
}
