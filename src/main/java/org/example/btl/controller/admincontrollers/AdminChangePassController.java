package org.example.btl.controller.admincontrollers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;

public class AdminChangePassController extends AdminBaseController{
    @Override
    public void setAdminInfo() {
    }

    Image hasEye = new Image("file:D:\\OOP\\BTL\\src\\main\\resources\\org\\example\\btl\\images\\eye.png");
    Image noEye = new Image("file:D:\\OOP\\BTL\\src\\main\\resources\\org\\example\\btl\\images\\hidden.png");

    public void switchEye(ImageView view) {
        if (view.getImage().equals(noEye)) {
            view.setImage(hasEye);
        } else {
            view.setImage(noEye);
        }
    }

    @FXML
    private ImageView eyeForPassword;

    public void switchEyeForPassword() {
        switchEye(eyeForPassword);
    }

    @FXML
    private ImageView eyeForConfirmPassword;

    public void switchEyeForConfirmPassword() {
        switchEye(eyeForConfirmPassword);
    }


}
