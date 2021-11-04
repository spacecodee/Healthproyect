package com.spacecodee.healthproyect.controllers.dashboard;

import com.spacecodee.healthproyect.controllers.user_roles.UserRoles;
import com.spacecodee.healthproyect.utils.AppUtils;
import com.spacecodee.healthproyect.utils.Images;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {

    @FXML
    private BorderPane bpContainer;

    @FXML
    private Button btnAddres;

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnReservationAppoiments;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnUserRoles;

    @FXML
    private Button btnUsers;

    @FXML
    private ImageView iconAddres;

    @FXML
    private ImageView iconDashboard;

    @FXML
    private ImageView iconLogout;

    @FXML
    private ImageView iconReservationA;

    @FXML
    private ImageView iconSettings;

    @FXML
    private ImageView iconUserRoles;

    @FXML
    private ImageView iconUsers;

    @FXML
    private VBox vbUsers;

    @Getter
    @Setter
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.loadIcons();
        this.loadTopBar();
    }

    @FXML
    private void userRolesOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnUserRoles)) {
            this.bpContainer.setCenter(null);

            FXMLLoader fxmlLoader = new FXMLLoader();
            BorderPane borderPane;

            try {
                fxmlLoader.setLocation(this.getClass().getResource(AppUtils.URL + "user-roles/users-rol.fxml"));
                borderPane = fxmlLoader.load();

                this.bpContainer.setCenter(borderPane);
            } catch (IOException e) {
                e.printStackTrace(System.out);
            }
        }
    }

    private void loadIcons() {
        ImageView[] imageViews = {
                this.iconDashboard, this.iconUsers, this.iconUserRoles,
                this.iconReservationA, this.iconAddres, this.iconSettings, this.iconLogout
        };

        final String[] iconsUrl = {
                "icons/dashboard.png",
                "icons/users.png",
                "icons/user-roles.png",
                "icons/reservations.png",
                "icons/address.png",
                "icons/settings.png",
                "icons/logout.png"
        };
        for (int i = 0; i < imageViews.length; i++) {
            Images.addImg(iconsUrl[i], imageViews[i]);
        }
    }

    private void loadTopBar() {
        var URL = "/com/spacecodee/healthproyect/view/components/top-bar.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader();
        HBox hBox = null;
        try {
            fxmlLoader.setLocation(this.getClass().getResource(URL));
            hBox = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        this.bpContainer.setTop(hBox);
    }
}
