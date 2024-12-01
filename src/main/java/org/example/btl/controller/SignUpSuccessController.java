package org.example.btl.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpSuccessController {

    /**
     * switch to log in scene.
     * @param event the action event triggered by the user.
     * @throws IOException if parent cannot load.
     */

    public void switchToLogin(ActionEvent event) throws IOException {
        String fxmlFile = "/org/example/btl/view/login-view.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
