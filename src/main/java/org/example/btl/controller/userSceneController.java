package org.example.btl.controller;

import jakarta.persistence.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.btl.model.Document;
import org.example.btl.model.HibernateUtils;
import org.example.btl.model.User;
import org.hibernate.Session;

import java.net.URL;
import java.sql.Date;
import java.util.*;

public class userSceneController implements Initializable {
    @FXML private Label userinfo;
    @FXML private TableView<Document> tableview;
    @FXML private TableColumn<Document, Integer> table_document_id;
    @FXML private TableColumn<Document, String> table_document_title;
    //@FXML private TableColumn<User, Date> table_user_duedate;
    List<User> users;
    Set<Document> documents  = new HashSet<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM User WHERE id = :id", User.class);
        query.setParameter("id", 1);
        users = query.getResultList();

        User user = users.getFirst();
        if(users.isEmpty()) {
            System.out.println("users is empty");
        } else {
            userinfo.setText(user.getName());
            // document
            Query query2 = session.createQuery("FROM Document", Document.class);
            List<Document> list_document ;
            list_document = query2.getResultList();
            for(Document x : list_document) {
                documents.add(x);
            }
            user.setBorrowedDocuments(new HashSet<>(list_document));
            session.save(user);

            table_document_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            table_document_title.setCellValueFactory(new PropertyValueFactory<>("title"));
            ObservableList<Document> documentlist = FXCollections.observableArrayList(
                    new Document(user.getBorrowedDocuments().iterator().next().getId(), user.getBorrowedDocuments().iterator().next().getTitle())
            );
            tableview.setItems(documentlist);

        }

    }

}
