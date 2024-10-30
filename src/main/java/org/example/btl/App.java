package org.example.btl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.btl.controllers.LoginController;
import org.example.btl.controllers.SignUpController;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        int check = 0;
        FXMLLoader fxmlLoader = null;
        if(check == 1) {
             fxmlLoader = new FXMLLoader(App.class.getResource("signUpScene.fxml"));
        } else if(check == 0) {
             fxmlLoader = new FXMLLoader(App.class.getResource("userScene.fxml"));
        }
            Parent root = fxmlLoader.load();

//        SignUpController signUpController = fxmlLoader.getController();

            Scene scene = new Scene(root);
            stage.setTitle("Library");
            stage.setScene(scene);
//        stage.setResizable(false);
            stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}