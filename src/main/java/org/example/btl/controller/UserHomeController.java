package org.example.btl.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.btl.model.Document;
import org.example.btl.model.HibernateUtils;
import org.example.btl.model.User;
import org.hibernate.Session;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class UserHomeController extends UserBaseController implements Initializable {
    @FXML
    private Label userinfo;
    @FXML
    private TableView<Document> tableview;
    @FXML
    private TableColumn<Document, Integer> table_document_id;
    @FXML
    private TableColumn<Document, String> table_document_title;
    //@FXML private TableColumn<User, Date> table_user_duedate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void setUserInfo() {
        //        try(Session session = HibernateUtils.getSessionFactory().openSession()) {
//            session.refresh(user);
////            Set<Document> borrowedDocs = user.getBorrowedDocuments();
//
//            table_document_id.setCellValueFactory(new PropertyValueFactory<>("id"));
//            table_document_title.setCellValueFactory(new PropertyValueFactory<>("title"));
//
//            ObservableList<Document> documentlist = FXCollections.observableArrayList(borrowedDocs);
//            tableview.setItems(documentlist);
//        }
        userinfo.setText(user.getName());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//            userinfo.setText(user.getName());

//            Query query2 = session.createQuery("FROM Document", Document.class);
//            List<Document> list_document ;
//            list_document = query2.getResultList();
//            for(Document x : list_document) {
//                documents.add(x);
//            }
//            user.setBorrowedDocuments(new HashSet<>(list_document));
//            session.save(user);
//
//            table_document_id.setCellValueFactory(new PropertyValueFactory<>("id"));
//            table_document_title.setCellValueFactory(new PropertyValueFactory<>("title"));
//            ObservableList<Document> documentlist = FXCollections.observableArrayList(
//                    new Document(user.getBorrowedDocuments().iterator().next().getId(), user.getBorrowedDocuments().iterator().next().getTitle())
//            );
//            tableview.setItems(documentlist);
    }


    // button to switch to user home
    public void switchToUserHomeScreen(ActionEvent event) throws IOException {

    }

    // button to switch to searchbook scene
    public void switchToUserSearchBook(ActionEvent event) throws IOException {

    }

    // button to switch to borrowbook scene
    public void switchToUserBorrowBook(ActionEvent event) throws IOException {

    }

    // button to switch to returnbook scene
    public void switchToUserReturnBook(ActionEvent event) throws IOException {

    }

    // button to switch to userInfo scene
    public void switchToUserInfo(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/userInforScene.fxml");
    }
}
