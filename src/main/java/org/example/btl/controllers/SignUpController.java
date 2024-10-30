package org.example.btl.controllers;

import jakarta.persistence.Query;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.btl.libraryManage.Account;
import org.example.btl.libraryManage.Admin;
import org.example.btl.libraryManage.HibernateUtils;
import org.hibernate.Session;

import javax.lang.model.element.AnnotationMirror;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

public class SignUpController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField nameText;
    @FXML
    private TextField emailText;
    @FXML
    private TextField usernameText;
    @FXML
    private PasswordField passwordText;
    @FXML
    private PasswordField confirmedPasswordText;
    @FXML
    private DatePicker birthdayText;
    @FXML
    private ToggleGroup gender;


    public void switchToLogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/btl/loginScene.fxml"));
        root = loader.load();
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void handleSignUp(ActionEvent event) {
        String name = nameText.getText();
        String email = emailText.getText();
        String username = usernameText.getText();
        String password = passwordText.getText();
        String confirmedPassword = confirmedPasswordText.getText();

        RadioButton selectedGender = (RadioButton) gender.getSelectedToggle();
        String gender = selectedGender == null ? "" : selectedGender.getText();

        Date birthday = birthdayText.getValue() == null ? null : Date.valueOf(birthdayText.getValue());

        if (Objects.equals(name, "")
            || Objects.equals(email, "")
            || Objects.equals(username, "")
            || Objects.equals(password, "")
            || Objects.equals(confirmedPassword, "")
            || Objects.equals(gender, "")
            || Objects.equals(birthday, null)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter all the information!");
            alert.show();
        } else if (!Objects.equals(password, confirmedPassword)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Passwords do not match. Please try again.");
            alert.show();
        } else if (username.length() < 6) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Your username must be at least 6 characters long.");
            alert.show();
        } else if (password.length() < 8) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Your Password must be at least 8 characters long.");
            alert.show();
        } else {
            Session session = HibernateUtils.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery("FROM Admin WHERE username = :username");
            query.setParameter("username", username);
            List admins = query.getResultList();
            session.close();
            HibernateUtils.shutdown();
            if (admins != null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("This username already exists. Please choose another one.");
                alert.show();
            } else {
                session.beginTransaction();
                Account admin = new Admin(name, email, username, password, birthday, gender);
                session.persist(admin);
                session.getTransaction().commit();
                session.close();
                HibernateUtils.shutdown();
            }
            //remember to change scene here
        }

    }
}
