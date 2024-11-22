package org.example.btl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.btl.dao.AdminDAO;
import org.example.btl.dao.UserDAO;
import org.example.btl.model.Admin;
import org.example.btl.model.HibernateUtils;
import org.example.btl.model.User;

import java.io.IOException;
import java.sql.Date;

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

//        new AdminDAO().save(new Admin("vegeta", "vegeta01@fg", "vegeta12020", "0123456789", Date.valueOf("2005-01-05"), "Male"));
//        new UserDAO().save(new User("Son Goku", "goku4554/@0dw", "goku01251ssj1000", "0123456789", Date.valueOf("2005-02-02"), "Male"));
//        HibernateUtils.shutdown();
    }
}