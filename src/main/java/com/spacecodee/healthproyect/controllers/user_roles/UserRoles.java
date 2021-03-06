package com.spacecodee.healthproyect.controllers.user_roles;

import com.spacecodee.healthproyect.controllers.modals.ModalConfirmation;
import com.spacecodee.healthproyect.dao.ICrudGeneric;
import com.spacecodee.healthproyect.dao.user_roles.UserRolesDaoImpl;
import com.spacecodee.healthproyect.dto.users_roles.UserRolesDto;
import com.spacecodee.healthproyect.utils.AppUtils;
import com.spacecodee.healthproyect.utils.Images;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UserRoles implements Initializable {

    @FXML
    private Button btnAddRol;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnDelete;

    @FXML
    private TableView<UserRolesDto> tableRolUsers;

    @FXML
    private TableColumn<UserRolesDto, Integer> idRolUser;

    @FXML
    private TableColumn<UserRolesDto, String> rol;

    @FXML
    private Label lblAddEditRol;

    @FXML
    private TextField txtRol;

    @FXML
    private TextField txtSearchRol;

    @FXML
    private BorderPane userRolSection;

    private final ICrudGeneric<UserRolesDto> userRolesDao = new UserRolesDaoImpl();

    private static String actionCrud = "add";

    private UserRolesDto userRolesDto;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.txtRol.addEventFilter(KeyEvent.KEY_TYPED, AppUtils.letterValidation(120));
        this.initTable();
    }

    private void initTable() {
        this.idRolUser.setCellValueFactory(new PropertyValueFactory<>("idRolUser"));
        this.rol.setCellValueFactory(new PropertyValueFactory<>("roleName"));

        this.loadTable();
    }

    private void loadTable() {
        this.tableRolUsers.setItems(this.loadUserRoles());
    }

    @FXML
    private void addRolOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnAddRol)) {
            if (!AppUtils.validateText(this.txtRol)) {
                if (UserRoles.actionCrud.equalsIgnoreCase("add")) {
                    this.add();
                } else if (UserRoles.actionCrud.equalsIgnoreCase("edit")) {
                    if (this.validateSelectedRoles()) {
                        this.loadModalConfirmation("??Estas seguro(a) que quieres editar");
                    } else {
                        AppUtils.loadModalMessage("Selecciona la fila a editar", "error");
                    }
                }
            } else {
                AppUtils.loadModalMessage("Todos los datos son necesarios", "error");
            }
        }
    }

    @FXML
    private void tblRolesUserOnClick(MouseEvent event) {
        if (event.getSource().equals(this.tableRolUsers)) {
            UserRoles.actionCrud = "edit";
            this.userRolesDto = this.tableRolUsers.getSelectionModel().getSelectedItem();
            if (this.userRolesDto != null) {
                this.changedCrudAction();
                this.txtRol.setText(this.userRolesDto.getRoleName());
            }
        }
    }

    @FXML
    private void cancelOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnCancel)) {
            this.reloadTableAndForm();
            UserRoles.actionCrud = "add";
            this.changedCrudAction();
        }
    }

    @FXML
    private void findByRolKeyTyped(KeyEvent event) {
        if (event.getSource().equals(this.txtSearchRol)) {
            var roleName = this.txtSearchRol.getText().trim().toUpperCase();

            if (roleName.isEmpty()) {
                this.tableRolUsers.setItems(this.loadUserRoles());
            } else {
                this.tableRolUsers.setItems(this.findUserRoles(roleName));
            }
        }
    }

    @FXML
    private void deleteRolOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnDelete)) {
            UserRoles.actionCrud = "delete";

            if (this.validateSelectedRoles()) {
                this.loadModalConfirmation("??Estas seguro(a) que quieres eliminar ");
            } else {
                AppUtils.loadModalMessage("Selecciona la fila a eliminar", "error");
            }
        }
    }

    private void changedCrudAction() {
        var title = (UserRoles.actionCrud.equalsIgnoreCase("add") ? "Agregar Rol" : "Actualizar Rol");
        this.lblAddEditRol.setText(title);
        this.btnAddRol.setText(title);
    }

    private boolean validateSelectedRoles() {
        return this.tableRolUsers.getSelectionModel().getSelectedItem() != null;
    }

    private void reloadTableAndForm() {
        AppUtils.clearText(this.txtRol);
        this.loadTable();
    }

    private void loadModalConfirmation(String message) {
        var stage = new Stage();

        final ModalConfirmation modalConfirmation = AppUtils.loadModalConfirmation(stage, message);

        modalConfirmation.getLblMessage().setText(message.toUpperCase());
        Images.addImg(AppUtils.urlAlert, modalConfirmation.getIconType());
        modalConfirmation.getBtnOk().setOnAction(actionEvent -> {
            if (UserRoles.actionCrud.equalsIgnoreCase("edit")) {
                this.edit(actionEvent);
            } else {
                this.delete(actionEvent);
            }
        });
        modalConfirmation.getBtnCancel().setOnAction(actionEvent -> {
            this.loadTable();
            AppUtils.closeModal(actionEvent);
        });

        stage.show();
    }

    private void add() {
        var roleName = this.txtRol.getText().trim().toUpperCase();
        var userRol = new UserRolesDto(roleName);

        if (this.userRolesDao.add(userRol)) {
            this.reloadTableAndForm();
            AppUtils.loadModalMessage("Rol de usuario agregado", "success");
        } else {
            AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde", "error");
        }
    }

    private void edit(ActionEvent actionEvent) {
        var roleName = this.txtRol.getText().trim().toUpperCase();
        this.userRolesDto.setRoleName(roleName);

        if (this.userRolesDao.update(this.userRolesDto)) {
            this.reloadTableAndForm();
            AppUtils.loadModalMessage("Rol de usuario actualizado", "success");
            AppUtils.closeModal(actionEvent);
        } else {
            AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde", "error");
        }

        UserRoles.actionCrud = "add";
        this.changedCrudAction();
    }

    private void delete(ActionEvent actionEvent) {
        if (this.userRolesDao.delete(this.userRolesDto)) {
            this.reloadTableAndForm();
            AppUtils.loadModalMessage("Rol " + this.userRolesDto.getRoleName()
                    + " eliminado con exito", "success");
            AppUtils.closeModal(actionEvent);
        } else {
            AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde", "error");
        }

        UserRoles.actionCrud = "add";
        this.changedCrudAction();
    }

    private ObservableList<UserRolesDto> loadUserRoles() {
        final ObservableList<UserRolesDto> observableArrayList = FXCollections.observableArrayList();
        observableArrayList.clear();
        observableArrayList.addAll(this.userRolesDao.load());

        return observableArrayList;
    }

    private ObservableList<UserRolesDto> findUserRoles(String name) {
        final ObservableList<UserRolesDto> observableArrayList = FXCollections.observableArrayList();
        observableArrayList.clear();
        observableArrayList.addAll(this.userRolesDao.findValue(new UserRolesDto(name)));

        return observableArrayList;
    }
}
