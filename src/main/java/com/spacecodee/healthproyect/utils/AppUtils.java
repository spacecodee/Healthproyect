package com.spacecodee.healthproyect.utils;

import com.spacecodee.healthproyect.controllers.add_modal.AddModal;
import com.spacecodee.healthproyect.controllers.cities.Cities;
import com.spacecodee.healthproyect.controllers.countries.Countries;
import com.spacecodee.healthproyect.controllers.districs.Districts;
import com.spacecodee.healthproyect.controllers.modals.ModalConfirmation;
import com.spacecodee.healthproyect.controllers.modals.ModalMessage;
import com.spacecodee.healthproyect.controllers.reservation_appointments.ReservationApModal;
import com.spacecodee.healthproyect.controllers.type_reservations.TypeReservations;
import com.spacecodee.healthproyect.model.countries.CountryModel;
import com.spacecodee.healthproyect.model.districts.DistrictModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;

public class AppUtils {
    public static final String URL_COMPONENTS = "/com/spacecodee/healthproyect/view/components/";
    public static final String URL_SHARED = "/com/spacecodee/healthproyect/view/shared/";
    public static final String URL = "/com/spacecodee/healthproyect/view/";
    public static final String URL_IMG = "src/main/resources/com/spacecodee/healthproyect/assets/icons/asteroid.png";
    public static final String urlAlert = "icons/modals/error.png";

    public static EventHandler<KeyEvent> numericValidation(final Integer maxLength) {
        return e -> {
            TextField txt_TextField = (TextField) e.getSource();
            if (txt_TextField.getText().length() >= maxLength) {
                e.consume();
            }
            if (e.getCharacter().matches("[0-9.]")) {
                if (txt_TextField.getText().contains(".") && e.getCharacter().matches("[.]")) {
                    e.consume();
                } else if (txt_TextField.getText().length() == 0 && e.getCharacter().matches("[.]")) {
                    e.consume();
                }
            } else {
                e.consume();
            }
        };
    }

    public static EventHandler<KeyEvent> letterValidation(final Integer maxLength) {
        return e -> {
            TextField txt_TextField = (TextField) e.getSource();
            if (txt_TextField.getText().length() >= maxLength) {
                e.consume();
            }
            if (!e.getCharacter().matches("[A-Za-z]")) {
                e.consume();
            }
        };
    }

    public static boolean validateText(TextField... text) {
        var validate = true;
        for (TextField textField : text) {
            validate = textField.getText().trim().isEmpty();
        }

        return validate;
    }

    public static void clearText(TextField... text) {
        for (TextField textField : text) {
            textField.setText("");
        }
    }

    public static Image loadIconApp() {
        File img = new File(AppUtils.URL_IMG);
        return new Image(img.toURI().toString());
    }

    public static void closeModal(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public static void closeModal(Button btn) {
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }

    public static void loadModalMessage(String message, String typeIcon) {
        final String urlSuccess = "icons/modals/success.png";
        final String urlAlert = "icons/modals/error.png";

        var stage = new Stage();

        var fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(AppUtils.class.getResource(AppUtils.URL + "modals/modal-message.fxml"));

        AppUtils.globalModal(stage, fxmlLoader, "Health Dashboard", 650, 250);

        final ModalMessage modalMessage = fxmlLoader.getController();
        modalMessage.getLblMessage().setText(message.toUpperCase());
        Images.addImg((typeIcon.equalsIgnoreCase("success") ? urlSuccess : urlAlert), modalMessage.getIconType());

        stage.show();
    }

    private static void globalModal(Stage stage, FXMLLoader fxmlLoader, String title, int width, int height) {
        try {
            var scene = new Scene(fxmlLoader.load(), width, height);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    public static ModalConfirmation loadModalConfirmation(Stage stage, final String MESSAGE) {
        var fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(AppUtils.class.getResource(AppUtils.URL + "modals/modal-confirmation.fxml"));

        AppUtils.globalModal(stage, fxmlLoader, MESSAGE, 650, 250);

        final ModalConfirmation modalConfirmation = fxmlLoader.getController();

        modalConfirmation.getLblMessage().setText(MESSAGE.toUpperCase());
        Images.addImg(AppUtils.urlAlert, modalConfirmation.getIconType());

        return modalConfirmation;
    }

    public static Countries loadCountriesModal(Stage stage, final String TITLE) {
        var fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(AppUtils.class.getResource(AppUtils.URL_COMPONENTS + "countries/countries.fxml"));

        AppUtils.globalModal(stage, fxmlLoader, TITLE, 650, 450);
        return fxmlLoader.getController();
    }

    public static Cities loadCitiesModal(Stage stage, final String TITLE) {
        var fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(AppUtils.class.getResource(AppUtils.URL_COMPONENTS + "cities/cities.fxml"));

        AppUtils.globalModal(stage, fxmlLoader, TITLE, 750, 450);
        return fxmlLoader.getController();
    }

    public static Districts loadDistrictsModal(Stage stage, final String TITLE) {
        var fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(AppUtils.class.getResource(AppUtils.URL_COMPONENTS + "districts/districts.fxml"));

        AppUtils.globalModal(stage, fxmlLoader, TITLE, 850, 450);
        return fxmlLoader.getController();
    }

    public static AddModal loadAddModal(Stage stage, final String TITLE) {
        var fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(AppUtils.class.getResource(AppUtils.URL_SHARED + "add-modal/add-modal.fxml"));

        AppUtils.globalModal(stage, fxmlLoader, TITLE, 750, 600);
        return fxmlLoader.getController();
    }

    public static TypeReservations loadTypeReservation(Stage stage, final String TITLE) {
        var fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(AppUtils.class.getResource(AppUtils.URL_COMPONENTS + "type_reservations/type-reservations.fxml"));

        AppUtils.globalModal(stage, fxmlLoader, TITLE, 750, 400);
        return fxmlLoader.getController();
    }

    public static ReservationApModal loadReservationsApModal(Stage stage, final String TITLE) {
        var fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(AppUtils.class.getResource(AppUtils.URL_COMPONENTS
                + "reservation_appointments/reservation-ap-modal.fxml"));

        AppUtils.globalModal(stage, fxmlLoader, TITLE, 750, 830);
        return fxmlLoader.getController();
    }

    public static int loadCities(ComboBox<CountryModel> cbxCountry) {
        if (!cbxCountry.getSelectionModel().isEmpty()) {
            return cbxCountry.getSelectionModel().getSelectedItem().getIdCountry();
        }

        return 0;
    }

    public static int loadDistricts(ComboBox<DistrictModel> cbxDistricts) {
        if (!cbxDistricts.getSelectionModel().isEmpty()) {
            return cbxDistricts.getSelectionModel().getSelectedItem().getIdDistrict();
        }

        return 0;
    }
}
