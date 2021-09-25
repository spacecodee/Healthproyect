package com.spacecodee.healthproyect.controllers.dashboard;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {

    @Getter
    @Setter
    private Stage stage;
    @FXML
    private BorderPane bpContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.loadTopBar();
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
