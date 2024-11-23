package org.example.btl.controller.usercontrollers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import org.example.btl.model.Document;
import org.example.btl.model.User;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
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
        userinfo.setText(user.getName());
        byte[] avatarData = user.getAvatar();
        if (avatarData != null) {
            InputStream inputStream = new ByteArrayInputStream(avatarData);
            avatar.setImage(new Image(inputStream));
        }
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
}
