package com.spacecodee.healthproyect.controllers.userroles;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class UserRoles implements Initializable {

    @FXML
    private Button btnAddRol;

    @FXML
    private Button btnCancel;

    @FXML
    private TableColumn<?, ?> idRolUser;

    @FXML
    private Label lblAddEditRol;

    @FXML
    private TableColumn<?, ?> rol;

    @FXML
    private TableView<?> tableRolUsers;

    @FXML
    private TextField txtRol;

    @FXML
    private TextField txtSearchRol;

    @FXML
    private BorderPane userRolSection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
