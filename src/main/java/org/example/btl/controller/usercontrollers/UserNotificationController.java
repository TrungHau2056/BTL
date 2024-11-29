package org.example.btl.controller.usercontrollers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.btl.controller.BookInfoController;
import org.example.btl.controller.NotificationController;
import org.example.btl.model.Borrow;
import org.example.btl.model.Document;
import org.example.btl.model.Notification;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class UserNotificationController extends UserBaseController implements Initializable {
    @FXML
    private Label totalLabel;
    @FXML
    private Label unreadLabel;

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

    private boolean isAllView = true;

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
        totalLabel.setText(String.valueOf(user.getNotifications().size()));
        int unreadCnt = 0;
        for (Notification notification : user.getNotifications()) {
            if (!notification.isRead()) {
                ++unreadCnt;
            }
        }
        unreadLabel.setText(String.valueOf(unreadCnt));

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

    public void handleInfo() throws IOException {
        Notification notification = tableView.getSelectionModel().getSelectedItem();
        if (notification == null) {
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/btl/view/notification-view.fxml"));
        Parent root = loader.load();

        NotificationController controller = loader.getController();
        controller.setUser(user);
        controller.setNotification(notification);
        controller.setUserNotificationController(this);
        controller.setNotificationInfo();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void handleMarkAsRead() {
        Task<Void> markAsReadTask = new Task<>() {
            @Override
            protected Void call() {
                for (Notification notification : user.getNotifications()) {
                    if (!notification.isRead()) {
                        user = notificationService.switchNotificationStatus(notification);
                    }
                }
                return null;
            }
        };

        markAsReadTask.setOnSucceeded(e -> {
            setUserInfo();
        });

        markAsReadTask.setOnFailed(e -> {
            alertErr.setContentText("Error: " + markAsReadTask.getException().getMessage());
            alertErr.show();
        });

        new Thread(markAsReadTask).start();
    }

    public void switchToUnread() {
        if (isAllView) {
            Task<List<Notification>> loadUnreadNotiTask = new Task<>() {
                @Override
                protected List<Notification> call() {
                    return notificationService.getUnreadNoti(user);
                }
            };

            loadUnreadNotiTask.setOnSucceeded(e -> {
                notifObservableList = FXCollections.observableArrayList(loadUnreadNotiTask.getValue());
                tableView.setItems(notifObservableList);
                isAllView = false;
            });

            loadUnreadNotiTask.setOnFailed(e -> {
                alertErr.setContentText("Error: " + loadUnreadNotiTask.getException().getMessage());
                alertErr.show();
            });

            new Thread(loadUnreadNotiTask).start();
        }
    }

    public void switchToAll() {
        if (!isAllView) {
            notifObservableList = FXCollections.observableArrayList(user.getNotifications());
            tableView.setItems(notifObservableList);
            isAllView = true;
        }
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

    public void handleDeleteAll() {
        user = notificationService.deleteAllNoti(user);
    }
}
