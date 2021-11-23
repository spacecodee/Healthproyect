package com.spacecodee.healthproyect.controllers.users;

import com.spacecodee.healthproyect.controllers.modals.ModalConfirmation;
import com.spacecodee.healthproyect.dao.address.AddressDaoImpl;
import com.spacecodee.healthproyect.dao.address.IAddressDao;
import com.spacecodee.healthproyect.dao.peoples.IPeopleDao;
import com.spacecodee.healthproyect.dao.peoples.PeopleDaoImpl;
import com.spacecodee.healthproyect.dao.users.IUserDao;
import com.spacecodee.healthproyect.dao.users.UserDaoImpl;
import com.spacecodee.healthproyect.dto.user.UserTable;
import com.spacecodee.healthproyect.model.peoples.PeopleModel;
import com.spacecodee.healthproyect.model.users.UserModel;
import com.spacecodee.healthproyect.utils.AppUtils;
import com.spacecodee.healthproyect.utils.Images;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

public class Users implements Initializable {

    @FXML
    private Button btnAddUsers;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnDeleteUsers;

    @FXML
    private TableView<UserTable> tableUsers;

    @FXML
    private TableColumn<UserTable, Integer> idUser;

    @FXML
    private TableColumn<UserTable, String> name;

    @FXML
    private TableColumn<UserTable, String> lastName;

    @FXML
    private TableColumn<UserTable, String> email;

    @FXML
    private TableColumn<UserTable, String> dni;

    @FXML
    private TableColumn<UserTable, String> phone;

    @FXML
    private TableColumn<UserTable, String> userName;

    @FXML
    private TableColumn<UserTable, String> rol;

    @FXML
    private TextField txtFindByDni;

    @FXML
    private TextField txtFindByUserName;

    private final IUserDao userDao = new UserDaoImpl();
    private final IAddressDao addressDao = new AddressDaoImpl();
    private final IPeopleDao peopleDao = new PeopleDaoImpl();

    @Getter
    @Setter
    private UserTable userTable;

    private static String actionCrud = "add";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.initTable();
    }

    private void initTable() {
        this.idUser.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        this.name.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        this.email.setCellValueFactory(new PropertyValueFactory<>("email"));
        this.dni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        this.phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        this.phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        this.userName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        this.rol.setCellValueFactory(new PropertyValueFactory<>("userRoleName"));

        this.loadTable();
    }

    private void loadTable() {
        this.tableUsers.setItems(this.userDao.loadTable());
    }

    @FXML
    private void btnAddOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnAddUsers)) {
            Stage stage = new Stage();
            if (Users.actionCrud.equalsIgnoreCase("Add")) {
                var addModalController = AppUtils.loadAddModal(stage, "Agregar Usuarios");
                addModalController.getLblTitle().setText("Agregar Usuario".toUpperCase());
                addModalController.getBtnSave().setOnAction(actionEvent -> {
                    if (!addModalController.validateTextFieldsUser()) {
                        var userModel = addModalController.returnUser();
                        this.add(userModel);
                        AppUtils.closeModal(actionEvent);
                    } else {
                        AppUtils.loadModalMessage("Todos los datos son necesarios", "error");
                    }
                });
            } else if (Users.actionCrud.equalsIgnoreCase("Edit")) {
                var addModalController = AppUtils.loadAddModal(stage, "Actualizar Usuarios");
                addModalController.getLblTitle().setText("Actualizar Usuario".toUpperCase());

                ///edit data
                addModalController.getTxtDni().setText(this.userTable.getDni());
                addModalController.getTxtName().setText(this.userTable.getName());
                addModalController.getTxtLastName().setText(this.userTable.getLastName());
                addModalController.getTxtEmail().setText(this.userTable.getEmail());
                addModalController.getTxtPhone().setText(this.userTable.getPhone());
                addModalController.setUserTable(this.userTable);

                ////////////////////////
                addModalController.getTxtPassword().setDisable(true);
                addModalController.getCbxCountry().setDisable(true);
                addModalController.getCbxCity().setDisable(true);
                addModalController.getCbxDistrict().setDisable(true);
                addModalController.getCbxRol().setDisable(true);
                addModalController.getDtBirthDate().setDisable(true);
                addModalController.getTxtUserName().setDisable(true);
                addModalController.getTxtAddress().setDisable(true);

                /////////
                addModalController.getBtnSave().setOnAction(actionEvent -> {
                    if (this.validateSelectedUserTable()) {
                        if (!addModalController.getTxtDni().getText().trim().isEmpty()
                                || !addModalController.getTxtName().getText().trim().isEmpty()
                                || !addModalController.getTxtLastName().getText().trim().isEmpty()
                                || !addModalController.getTxtEmail().getText().trim().isEmpty()
                                || !addModalController.getTxtPhone().getText().trim().isEmpty()) {
                            var peopleModel = addModalController.returnUser().getPeople();
                            this.loadModalConfirmation("¿Estas seguro(a) que quieres editar a este usuario?", actionEvent, peopleModel);
                        }
                    } else {
                        AppUtils.loadModalMessage("Todos los datos son necesarios", "error");
                    }
                });
            }
            stage.show();
        }
    }

    private void add(UserModel userModel) {
        if (this.addressDao.add(userModel.getPeople().getAddressModel())) {
            var idAddress = this.addressDao.returnMaxId();
            if (idAddress != 0) {
                userModel.getPeople().getAddressModel().setIdAddress(idAddress);
                if (this.peopleDao.add(userModel.getPeople())) {
                    var idPeople = this.peopleDao.returnMaxId();
                    if (idPeople != 0) {
                        userModel.getPeople().setIdPeople(idPeople);

                        if (this.userDao.add(userModel)) {
                            AppUtils.loadModalMessage("Usuario Agregado", "success");
                        } else {
                            AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde", "error");
                        }

                        this.loadTable();
                    }
                }
            }
        } else {
            AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde", "error");
        }

    }

    @FXML
    private void btnCancelOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnCancel)) {
            this.loadTable();
            this.changedCrudAction("Add");
        }
    }

    @FXML
    private void btnDeleteOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnDeleteUsers)) {
            Users.actionCrud = "delete";
            this.changedCrudAction("Add");

            if (this.validateSelectedUserTable()) {
                this.loadModalConfirmation("¿Estas seguro(a) que quieres eliminar esta dirección", null, null);
            } else {
                AppUtils.loadModalMessage("Selecciona la fila a eliminar", "error");
            }
        }
    }

    private void loadModalConfirmation(String message, ActionEvent actionEvent2, PeopleModel peopleModel) {
        var stage = new Stage();
        final ModalConfirmation modalConfirmation = AppUtils.loadModalConfirmation(stage, message);

        modalConfirmation.getLblMessage().setText(message.toUpperCase());
        Images.addImg(AppUtils.urlAlert, modalConfirmation.getIconType());
        modalConfirmation.getBtnOk().setOnAction(actionEvent -> {
            if (Users.actionCrud.equalsIgnoreCase("edit")) {
                this.edit(peopleModel, actionEvent, actionEvent2);
            } else {
                this.delete(actionEvent);
            }
        });
        modalConfirmation.getBtnCancel().setOnAction(actionEvent -> {
            this.changedCrudAction("Add");
            AppUtils.closeModal(actionEvent);
        });

        stage.show();
    }

    private void delete(ActionEvent actionEvent) {
        var idPeople = this.tableUsers.getSelectionModel().getSelectedItem().getIdPeople();
        var peopleModel = new PeopleModel(idPeople);
        if (this.peopleDao.delete(peopleModel)) {
            AppUtils.loadModalMessage("Usuario eliminado con exito", "success");
            AppUtils.closeModal(actionEvent);
        } else {
            AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde", "error");
        }

        this.loadTable();
        this.changedCrudAction("add");
    }

    private void edit(PeopleModel peopleModel, ActionEvent... actionEvent) {
        if (this.peopleDao.update(peopleModel)) {
            AppUtils.loadModalMessage("Usuario Actualizado", "success");
        } else {
            AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde", "error");
        }

        for (ActionEvent event : actionEvent) {
            AppUtils.closeModal(event);
        }

        this.loadTable();
        this.changedCrudAction("add");
    }

    @FXML
    private void findByOnTyped(KeyEvent event) {
        if (event.getSource().equals(this.txtFindByDni) || event.getSource().equals(this.txtFindByUserName)) {
            var dni = this.txtFindByDni.getText().trim();
            var userName = this.txtFindByUserName.getText().trim();

            if (dni.isEmpty() || userName.isEmpty()) {
                this.loadTable();
            } else {
                var userTable = new UserTable(dni, userName);
                this.tableUsers.setItems(this.userDao.findByNameAndDniTable(userTable));
            }

        }
    }

    @FXML
    private void tblUsersOnClick(MouseEvent event) {
        if (event.getSource().equals(this.tableUsers)) {
            this.changedCrudAction("edit");
            this.setUserTable(this.tableUsers.getSelectionModel().getSelectedItem());
        }
    }

    private boolean validateSelectedUserTable() {
        return this.tableUsers.getSelectionModel().getSelectedItem() != null;
    }

    private void changedCrudAction(String type) {
        Users.actionCrud = type;
        this.btnAddUsers.setText(type.equalsIgnoreCase("Edit") ? "Editar" : "Agregar");
    }
}
