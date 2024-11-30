package org.example.btl.controller.usercontrollers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
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
import org.example.btl.controller.NotificationController;
import org.example.btl.model.Notification;
import org.example.btl.model.User;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

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

    /**
     * set up tableview.
     * @param url
     * @param resourceBundle
     */

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

    /**
     * set user for scene.
     */

    @Override
    public void setUserInfo() {
        Set<Notification> notifications = userService.getNotifications(user);

        totalLabel.setText(String.valueOf(notifications.size()));
        int unreadCnt = 0;
        for (Notification notification : notifications) {
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

        notifObservableList = FXCollections.observableArrayList(notifications);
        tableView.setItems(notifObservableList);
    }

    /**
     * click remove button.
     */

    public void handleRemove() {
        Notification selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            user = notificationService.deleteNotification(selectedItem);

            setUserInfo();
        } else {
            alertErr.setContentText("Please choose an item");
            alertErr.show();
        }
    }

    /**
     * click Info button.
     * @throws IOException
     */

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

    /**
     * click 'mark as read' button.
     */

    public void handleMarkAsRead() {
        Task<Void> markAsReadTask = new Task<>() {
            @Override
            protected Void call() {
                for (Notification notification : userService.getNotifications(user)) {
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

    /**
     * switch table to unread.
     */

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

    /**
     * switch table to all.
     */

    public void switchToAll() {
        if (!isAllView) {
            notifObservableList = FXCollections.observableArrayList(userService.getNotifications(user));
            tableView.setItems(notifObservableList);
            isAllView = true;
        }
    }

    /**
     * click remove all button.
     */

    public void handleDeleteAll() {
        Task<User> deleteAllTask = new Task<>() {
            @Override
            protected User call() {
                return notificationService.deleteAllNoti(user);
            }
        };

        deleteAllTask.setOnSucceeded(e -> {
            user = deleteAllTask.getValue();
            setUserInfo();
        });

        deleteAllTask.setOnFailed(e -> {
            alertErr.setContentText("Error: " + deleteAllTask.getException().getMessage());
            alertErr.show();
        });

        new Thread(deleteAllTask).start();
    }
}
