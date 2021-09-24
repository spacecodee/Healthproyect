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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Images.addImg("img/profile.jpg", this.imgProfile);
    }
}
