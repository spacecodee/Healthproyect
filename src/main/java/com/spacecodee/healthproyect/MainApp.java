package com.spacecodee.healthproyect;

import com.spacecodee.healthproyect.controllers.dashboard.Dashboard;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainApp extends Application {
    private static final String URL = "/com/spacecodee/healthproyect/view/dashboard/";
    private static final String URL_IMG = "src/main/resources/com/spacecodee/healthproyect/assets/icons/asteroid.png";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(MainApp.class.getResource(URL + "dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);

        Dashboard dashboard = fxmlLoader.getController();
        dashboard.setStage(stage);

        File img = new File(MainApp.URL_IMG);
        Image icon = new Image(img.toURI().toString());
        stage.getIcons().add(icon);
        stage.setTitle("Health Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}