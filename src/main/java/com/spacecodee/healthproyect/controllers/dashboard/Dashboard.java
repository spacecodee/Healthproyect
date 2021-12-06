package com.spacecodee.healthproyect.controllers.dashboard;

import com.spacecodee.healthproyect.controllers.menu.Menu;
import com.spacecodee.healthproyect.controllers.modals.ModalConfirmation;
import com.spacecodee.healthproyect.controllers.settings.Settings;
import com.spacecodee.healthproyect.controllers.top_bar.TopBar;
import com.spacecodee.healthproyect.dto.users.UserDto;
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
    private Button btnAddress;

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
    private Button btnCustomers;

    @FXML
    private ImageView iconAddress;

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
    private ImageView iconCustomer;

    @Getter
    @FXML
    private VBox vbUsers;

    @Getter
    @Setter
    private UserDto userDto;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.loadIcons();
    }

    @FXML
    private void dashboardOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnDashboard)) {
            this.loadDashboard();
        }
    }

    @FXML
    private void btnUsersOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnUsers)) {
            this.bpContainer.setCenter(null);

            FXMLLoader fxmlLoader = new FXMLLoader();
            BorderPane borderPane;

            try {
                fxmlLoader.setLocation(this.getClass().getResource(AppUtils.URL_COMPONENTS + "users/users.fxml"));
                borderPane = fxmlLoader.load();

                this.bpContainer.setCenter(borderPane);
            } catch (IOException e) {
                e.printStackTrace(System.out);
            }
        }
    }

    @FXML
    private void userRolesOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnUserRoles)) {
            this.bpContainer.setCenter(null);

            FXMLLoader fxmlLoader = new FXMLLoader();
            BorderPane borderPane;

            try {
                fxmlLoader.setLocation(this.getClass().getResource(AppUtils.URL_COMPONENTS + "user-roles/users-rol.fxml"));
                borderPane = fxmlLoader.load();

                this.bpContainer.setCenter(borderPane);
            } catch (IOException e) {
                e.printStackTrace(System.out);
            }
        }
    }

    @FXML
    private void addressOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnAddress)) {
            this.bpContainer.setCenter(null);

            FXMLLoader fxmlLoader = new FXMLLoader();
            BorderPane borderPane;

            try {
                fxmlLoader.setLocation(this.getClass().getResource(AppUtils.URL_COMPONENTS + "address/address.fxml"));
                borderPane = fxmlLoader.load();

                this.bpContainer.setCenter(borderPane);
            } catch (IOException e) {
                e.printStackTrace(System.out);
            }
        }
    }

    @FXML
    private void customersOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnCustomers)) {
            this.bpContainer.setCenter(null);

            FXMLLoader fxmlLoader = new FXMLLoader();
            BorderPane borderPane;

            try {
                fxmlLoader.setLocation(this.getClass().getResource(AppUtils.URL_COMPONENTS + "customers/customers.fxml"));
                borderPane = fxmlLoader.load();

                this.bpContainer.setCenter(borderPane);
            } catch (IOException e) {
                e.printStackTrace(System.out);
            }
        }
    }

    @FXML
    private void reservationOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnReservationAppoiments)) {
            this.bpContainer.setCenter(null);

            FXMLLoader fxmlLoader = new FXMLLoader();
            BorderPane borderPane;

            try {
                fxmlLoader.setLocation(this.getClass().getResource(AppUtils.URL_COMPONENTS
                        + "reservation_appointments/reservation_appointments.fxml"));
                borderPane = fxmlLoader.load();

                this.bpContainer.setCenter(borderPane);
            } catch (IOException e) {
                e.printStackTrace(System.out);
            }
        }
    }

    @FXML
    private void settingsOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnSettings)) {
            this.bpContainer.setCenter(null);

            FXMLLoader fxmlLoader = new FXMLLoader();
            VBox vBox;

            try {
                fxmlLoader.setLocation(this.getClass().getResource(AppUtils.URL_COMPONENTS + "settings/settings.fxml"));
                vBox = fxmlLoader.load();
                Settings settings = fxmlLoader.getController();
                settings.setUserDto(this.userDto);
                settings.sendData();

                this.bpContainer.setCenter(vBox);
            } catch (IOException e) {
                e.printStackTrace(System.out);
            }
        }
    }

    @FXML
    private void logoutOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnLogout)) {
            this.loadModalConfirmation(event);
        }
    }

    private void loadDashboard() {
        this.bpContainer.setCenter(null);

        FXMLLoader fxmlLoader = new FXMLLoader();
        BorderPane borderPane;

        try {
            fxmlLoader.setLocation(this.getClass().getResource(AppUtils.URL_COMPONENTS + "menu/menu.fxml"));
            borderPane = fxmlLoader.load();
            Menu menu = fxmlLoader.getController();
            menu.getLblTitle().setText(("Hola " + this.userDto.getUserName() + ", ten un día productivo").toUpperCase());

            this.bpContainer.setCenter(borderPane);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    private void loadIcons() {
        ImageView[] imageViews = {
                this.iconDashboard, this.iconUsers, this.iconUserRoles,
                this.iconCustomer, this.iconReservationA, this.iconAddress,
                this.iconSettings, this.iconLogout
        };

        final String[] iconsUrl = {
                "icons/dashboard.png",
                "icons/users.png",
                "icons/user-roles.png",
                "icons/customer.png",
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
        var URL = AppUtils.URL_SHARED + "top-bar/top-bar.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader();
        HBox hBox;
        try {
            fxmlLoader.setLocation(this.getClass().getResource(URL));
            hBox = fxmlLoader.load();
            TopBar topBar = fxmlLoader.getController();
            topBar.getLblUsername().setText(this.userDto.getUserName().toUpperCase());

            this.bpContainer.setTop(hBox);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    public void initDashboardLogin() {
        this.loadTopBar();
        this.loadDashboard();

        if (this.userDto.getUserRolesDto().getRoleName().equalsIgnoreCase("usuario")) {
            this.vbUsers.getChildren().remove(1);
            this.vbUsers.getChildren().remove(1);
        }
    }

    private void loadModalConfirmation(ActionEvent event) {
        var stage = new Stage();
        final ModalConfirmation modalConfirmation = AppUtils.loadModalConfirmation(stage,
                "¿Estas seguro(a) que quieres cerrar sesión?");

        modalConfirmation.getLblMessage().setText("¿Estas seguro(a) que quieres cerrar sesión?".toUpperCase());
        Images.addImg(AppUtils.urlAlert, modalConfirmation.getIconType());
        modalConfirmation.getBtnOk().setOnAction(actionEvent -> {
            AppUtils.loadLogin(this.userDto);
            AppUtils.closeModal(event);
            AppUtils.closeModal(actionEvent);
        });
        modalConfirmation.getBtnCancel().setOnAction(AppUtils::closeModal);

        stage.show();
    }
}
