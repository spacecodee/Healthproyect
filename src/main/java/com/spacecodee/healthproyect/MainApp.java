package com.spacecodee.healthproyect;

import com.spacecodee.healthproyect.controllers.dashboard.Dashboard;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(MainApp.class.getResource("/com/spacecodee/healthproyect/view/dashboard/dashboard" +
                ".fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        Dashboard dashboard = fxmlLoader.getController();
        dashboard.setStage(stage);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}