package org.example.btl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.btl.dao.DocumentDAO;
import org.example.btl.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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


//        Session session = HibernateUtils.getSessionFactory().openSession();
//        Transaction transaction = null;
//        Set<Author> authors = new HashSet<>();
//
//        try {
//            // Bắt đầu giao dịch
//            transaction = session.beginTransaction();
//
//            int documentId = 1; // Thay bằng ID của document cần cập nhật
//            Document document = session.get(Document.class, documentId);
//
//            if (document != null) {
//                // Lấy đối tượng Admin từ cơ sở dữ liệu
//                Author author = session.get(Author.class, 1);
//                authors.add(author);
//
//                if (author != null) {
//                    document.setAuthors(authors);
//
//                    // Lưu các thay đổi
//                    session.update(document);
//                    transaction.commit();
//                    System.out.println("Document đã được cập nhật thành công.");
//                } else {
//                    System.out.println("Không tìm thấy Admin với ID = 1.");
//                }
//            } else {
//                System.out.println("Không tìm thấy document với ID: " + documentId);
//            }
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            e.printStackTrace();
//        } finally {
//            session.close();
//        }


    }


    public static void main(String[] args) {
        launch(args);
        HibernateUtils.shutdown();
    }
}