package org.example.btl;

import com.google.api.services.books.v1.model.Usersettings;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.http.impl.client.AutoRetryHttpClient;
import org.example.btl.dao.AuthorDAO;
import org.example.btl.dao.GenreDAO;
import org.example.btl.model.Author;
import org.example.btl.model.Document;
import org.example.btl.model.Genre;
import org.example.btl.model.HibernateUtils;
import org.example.btl.service.DocumentService;
import org.hibernate.Session;

import java.io.IOException;

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
        Document document = new DocumentService().findByTitle("Hello World");
        Author author = new AuthorDAO().findByName("Hellozx");
        Genre genre = new GenreDAO().findByName("Action");

        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        author.addDocument(document);
        genre.addDocument(document);
        session.merge(document);

        session.getTransaction().commit();
        session.close();

        launch(args);
        HibernateUtils.shutdown();
    }
}