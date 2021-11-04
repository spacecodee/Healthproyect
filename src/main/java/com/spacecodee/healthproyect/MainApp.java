package com.spacecodee.healthproyect;

import com.spacecodee.healthproyect.controllers.dashboard.Dashboard;
import com.spacecodee.healthproyect.utils.AppUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(MainApp.class.getResource(AppUtils.URL + "dashboard/dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);

        Dashboard dashboard = fxmlLoader.getController();
        dashboard.setStage(stage);

        stage.getIcons().add(AppUtils.loadIconApp());
        stage.setTitle("Health Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}