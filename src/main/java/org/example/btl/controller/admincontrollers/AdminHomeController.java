package org.example.btl.controller.admincontrollers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminHomeController extends AdminBaseController {
    /**
     * Sets the information for the admin.
     * This method updates the name label with the admin's name,
     * greeting the admin with a personalized message.
     */
    @Override
    public void setAdminInfo() {
        nameLabel.setText("Hi " + admin.getName());
    }

    /**
     * Handles the action of opening the "Change Password" scene.
     * This method loads the `adminChangePassword-view.fxml`, initializes the controller,
     * and sets the admin and home controller information.
     * A new stage is created to display the change password screen.
     *
     * @throws IOException If loading the FXML file fails.
     */
    public void handleChangePass() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/btl/view/adminview/adminChangePassword-view.fxml"));
        Parent root = loader.load();

        AdminChangePassController controller = loader.getController();
        controller.setAdmin(admin);
        controller.setAdminHomeController(this);
        controller.setAdminInfo();

        Stage stage = new Stage();
        stage.setTitle("Change Password");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
