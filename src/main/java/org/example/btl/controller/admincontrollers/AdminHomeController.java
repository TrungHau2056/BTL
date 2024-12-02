package org.example.btl.controller.admincontrollers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminHomeController extends AdminBaseController {
    @Override
    public void setAdminInfo() {
        nameLabel.setText("Hi " + admin.getName());
    }

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
