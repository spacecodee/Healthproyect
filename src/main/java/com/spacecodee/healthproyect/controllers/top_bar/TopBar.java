package com.spacecodee.healthproyect.controllers.top_bar;

import com.spacecodee.healthproyect.utils.Images;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

public class TopBar implements Initializable {

    @FXML
    private ImageView imgProfile;
    @FXML
    private ImageView imgBell;
    @FXML
    private ImageView imgLogo;
    @Getter
    @Setter
    @FXML
    private Label lblUsername;

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
