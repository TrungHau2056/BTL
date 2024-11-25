package org.example.btl.controller.usercontrollers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import org.example.btl.model.Borrow;
import org.example.btl.model.Document;
import org.example.btl.model.Notification;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class UserNotificationController extends UserBaseController implements Initializable {
    @FXML
    private TableView<Notification> tableView;

    @FXML
    private TableColumn<Notification, Integer> idCol;
    @FXML
    private TableColumn<Notification, String> durationCol;
    @FXML
    private TableColumn<Notification, String> titleCol;
    @FXML
    private TableColumn<Notification, String> statusCol;

    private ObservableList<Notification> notifObservableList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        statusCol.setCellValueFactory(
                data -> new SimpleStringProperty(
                        data.getValue().isRead() == true ? "Read" : "Unread"
                )
        );

        durationCol.setCellValueFactory(
                data -> new SimpleStringProperty(
                        notificationService.getDuration(data.getValue().getCreatedAt())
                )
        );
    }

    @Override
    public void setUserInfo() {
        nameLabel.setText(user.getName());
        byte[] avatarData = user.getAvatar();
        if (avatarData != null) {
            InputStream inputStream = new ByteArrayInputStream(avatarData);
            avatar.setImage(new Image(inputStream));
        }

        notifObservableList = FXCollections.observableArrayList(user.getNotifications());
        tableView.setItems(notifObservableList);
    }

    public void refresh() {
        tableView.refresh();
    }

    public void handleRemove() {
        Notification selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            user = notificationService.deleteNotification(selectedItem);

            setUserInfo();
            refresh();
        } else {
            alertErr.setContentText("Please choose an item");
            alertErr.show();
        }
    }

    public void handleNote() {

    }

    public void switchToHistoryScene(ActionEvent event) {

    }

    public void handleTableClick() {
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Notification selectedNotification = tableView.getSelectionModel().getSelectedItem();
                if (selectedNotification != null) {
                    System.out.println("okay");
                    //showNotificationDetails(selectedNotification);
                }
            }
        });
    }
}
