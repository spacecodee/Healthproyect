package com.spacecodee.healthproyect.utils;

import com.spacecodee.healthproyect.controllers.modals.ModalConfirmation;
import com.spacecodee.healthproyect.controllers.modals.ModalMessage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;

public class AppUtils {
    public static final String URL = "/com/spacecodee/healthproyect/view/";
    public static final String URL_IMG = "src/main/resources/com/spacecodee/healthproyect/assets/icons/asteroid.png";

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

        AppUtils.globalModal(stage, fxmlLoader);

        final ModalMessage modalMessage = fxmlLoader.getController();
        modalMessage.getLblMessage().setText(message.toUpperCase());
        Images.addImg((typeIcon.equalsIgnoreCase("success") ? urlSuccess : urlAlert), modalMessage.getIconType());

        stage.show();
    }

    public static void globalModal(Stage stage, FXMLLoader fxmlLoader) {
        try {
            var scene = new Scene(fxmlLoader.load(), 650, 250);
            stage.setTitle("Health Dashboard");
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
