package com.spacecodee.healthproyect.controllers.components;

import com.spacecodee.healthproyect.utils.Images;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class TopBar implements Initializable {
    @FXML
    private ImageView imgProfile;
    @FXML
    private ImageView imgBell;
    @FXML
    private ImageView imgLogo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.initComponents();
    }

    private void initComponents() {
        Images.addImg("img/profile.jpg", this.imgProfile);
        Images.addImg("icons/bell.png", this.imgBell);
        Images.addImg("icons/asteroid-black.png", this.imgLogo);
        Images.borderRadiusImgProfile(this.imgProfile, 100);
    }
}
