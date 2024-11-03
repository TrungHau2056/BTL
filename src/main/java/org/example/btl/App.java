package org.example.btl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.btl.model.HibernateUtils;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = null;


             fxmlLoader = new FXMLLoader(App.class.getResource("view/signUpScene.fxml"));
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
        HibernateUtils.shutdown();
    }
}