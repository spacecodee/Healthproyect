package com.spacecodee.healthproyect.controllers.modals;

import com.spacecodee.healthproyect.utils.AppUtils;
import com.spacecodee.healthproyect.utils.Images;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import lombok.Getter;

import java.net.URL;
import java.util.ResourceBundle;

public class ModalMessage implements Initializable {

    @FXML
    private Button btnOk;

    @Getter
    @FXML
    private ImageView iconType;

    @Getter
    @FXML
    private Label lblMessage;

    static int test = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Images.addImg("icons/modals/success.png", this.iconType);
    }

    @FXML
    private void okOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnOk)) {
            AppUtils.closeModal(event);
        }
    }
}
