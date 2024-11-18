package org.example.btl.controller.admincontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.example.btl.model.Admin;
import org.example.btl.service.AdminService;
import org.example.btl.service.DocumentService;
import org.example.btl.service.UserService;

import java.io.IOException;

public abstract class AdminBaseController {
    protected Admin admin;

    protected AdminService adminService = new AdminService();
    protected UserService userService = new UserService();
    protected DocumentService documentService = new DocumentService();

    protected Alert alertErr = new Alert(Alert.AlertType.ERROR);
    protected Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
    protected Alert alertComfirm = new Alert(Alert.AlertType.CONFIRMATION);

    protected Stage stage;
    protected Parent root;
    protected Scene scene;

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public abstract void setAdminInfo();

    public void switchScene(ActionEvent event, String path) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        root = loader.load();
        AdminBaseController controller = loader.getController();
        controller.setAdmin(admin);
        controller.setAdminInfo();

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToAdminHome(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/adminview/adminHome-view.fxml");
    }

    public void switchToAdminSearchBook(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/adminview/adminSearchBook-view.fxml");
    }

    public void switchToAddMemberScene(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/adminview/addMember-view.fxml");
    }

    public void switchToAddBookScene(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/adminview/adminAddBook-view.fxml");
    }

    public void switchToDeleteBookScene(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/adminview/adminDeleteBook-view.fxml");
    }

    public void switchToISBNScene(ActionEvent event) throws IOException {
        switchScene(event, "/org/example/btl/view/adminview/addByISBN-view.fxml");
    }

}
