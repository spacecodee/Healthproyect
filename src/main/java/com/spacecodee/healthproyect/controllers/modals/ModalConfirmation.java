package com.spacecodee.healthproyect.controllers.modals;

import com.spacecodee.healthproyect.controllers.user_roles.UserRoles;
import com.spacecodee.healthproyect.utils.AppUtils;
import com.spacecodee.healthproyect.utils.Images;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

public class ModalConfirmation implements Initializable {

    @Getter
    @Setter
    @FXML
    private Button btnCancel;

    @Getter
    @Setter
    @FXML
    private Button btnOk;

    @Getter
    @Setter
    @FXML
    private ImageView iconType;

    @Getter
    @Setter
    @FXML
    private Label lblMessage;

    @Getter
    @Setter
    private boolean confirmation;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Images.addImg("icons/modals/success.png", this.iconType);
    }

    @FXML
    private void cancelOnAction(ActionEvent event) {
        AppUtils.closeModal(event);
    }

    @FXML
    private void okOnAction(ActionEvent event) {

    }
}
