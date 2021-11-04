package com.spacecodee.healthproyect.controllers.userroles;

import com.spacecodee.healthproyect.dao.ICrudGeneric;
import com.spacecodee.healthproyect.dao.userroles.UserRolesDaoImpl;
import com.spacecodee.healthproyect.model.users_roles.UserRolesModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class UserRoles implements Initializable {

    @FXML
    private Button btnAddRol;

    @FXML
    private Button btnCancel;

    @FXML
    private TableView<UserRolesModel> tableRolUsers;

    @FXML
    private TableColumn<UserRolesModel, Integer> idRolUser;

    @FXML
    private TableColumn<UserRolesModel, String> rol;

    @FXML
    private Label lblAddEditRol;

    @FXML
    private TextField txtRol;

    @FXML
    private TextField txtSearchRol;

    @FXML
    private BorderPane userRolSection;

    private final ICrudGeneric<UserRolesModel> userRolesDao = new UserRolesDaoImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.loadTable();
    }

    private void loadTable() {
        this.idRolUser.setCellValueFactory(new PropertyValueFactory<>("idRolUser"));
        this.rol.setCellValueFactory(new PropertyValueFactory<>("roleName"));

        this.tableRolUsers.setItems(this.userRolesDao.load());
    }

    @FXML
    private void addRolOnAction(ActionEvent event) {

    }

    @FXML
    private void cancelOnAction(ActionEvent event) {

    }

    @FXML
    private void findByRolKeyTyped(KeyEvent event) {

    }
}
