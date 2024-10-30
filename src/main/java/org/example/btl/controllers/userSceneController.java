package org.example.btl.controllers;

import jakarta.persistence.Query;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.example.btl.libraryManage.HibernateUtils;
import org.example.btl.libraryManage.User;
import org.hibernate.Session;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class userSceneController implements Initializable {
    @FXML private Label userinfo;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM User WHERE id = :id");
        query.setParameter("id", 1);
        List<User> users = query.getResultList();
        if(users.isEmpty()) {
            System.out.println("users is empty");
        } else {
            userinfo.setText(users.getFirst().getName());
        }
    }

}
