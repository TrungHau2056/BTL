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

    /**
     * Sets the reference to the AdminHomeController.
     * This method allows the setting of the AdminHomeController instance.
     *
     * @param adminHomeController The AdminHomeController instance to be set.
     */
    public void setAdminHomeController(AdminHomeController adminHomeController) {
        this.adminHomeController = adminHomeController;
    }

    /**
     * Toggles the visibility of the current password text.
     * If the password is currently hidden, it shows the password and changes the eye button style.
     * If the password is currently visible, it hides the password and changes the eye button style.
     */
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

    /**
     * Toggles the visibility of the new password text.
     * If the new password is currently hidden, it shows the password and changes the eye button style.
     * If the new password is currently visible, it hides the password and changes the eye button style.
     */
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

    /**
     * Sets the information for the admin.
     * This method is intended to be implemented with functionality for setting admin-specific information.
     * Currently, it does not perform any action.
     */
    @Override
    public void setAdminInfo() {
    }

    /**
     * Handles the confirmation of password change.
     * This method validates the current password, checks if the new password is entered and matches the confirmation password.
     * If the validation passes, the password is updated for the admin.
     * It also displays appropriate error or success messages.
     *
     * @param event The action event triggered by the user confirming the password change.
     */
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
