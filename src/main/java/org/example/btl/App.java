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
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view/login-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Library");
        stage.setScene(scene);
//        stage.setResizable(false);
        stage.show();


//        Session session = HibernateUtils.getSessionFactory().openSession();
//        Transaction transaction = null;
//
//        try {
//            // Bắt đầu giao dịch
//            transaction = session.beginTransaction();
//            User user = session.get(User.class, 1);
//
//            if (user != null) {
//
//                Borrow borrow = session.get(Borrow.class, 2);
//                borrow.setUser(user);
//                if (borrow != null) {
//                    session.update(borrow);
//                    transaction.commit();
//                    System.out.println("Document đã được cập nhật thành công.");
//                } else {
//                    System.out.println("borrow");
//                }
//
//            } else {
//                System.out.println("Không tìm thấy user với ID: ");
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