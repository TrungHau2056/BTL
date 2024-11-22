package org.example.btl;

import jakarta.persistence.Query;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.btl.model.Admin;
import org.example.btl.model.Document;
import org.example.btl.model.HibernateUtils;
import org.example.btl.service.DocumentService;
import org.hibernate.Session;

import java.io.IOException;
import java.util.List;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view/login-view.fxml"));
        Parent root = fxmlLoader.load();
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