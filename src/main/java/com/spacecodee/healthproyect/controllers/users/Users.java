package com.spacecodee.healthproyect.controllers.users;

import com.spacecodee.healthproyect.controllers.modals.ModalConfirmation;
import com.spacecodee.healthproyect.dao.address.AddressDaoImpl;
import com.spacecodee.healthproyect.dao.address.IAddressDao;
import com.spacecodee.healthproyect.dao.peoples.IPeopleDao;
import com.spacecodee.healthproyect.dao.peoples.PeopleDaoImpl;
import com.spacecodee.healthproyect.dao.users.IUserDao;
import com.spacecodee.healthproyect.dao.users.UserDaoImpl;
import com.spacecodee.healthproyect.converters.user.UserTable;
import com.spacecodee.healthproyect.dto.peoples.PeopleDto;
import com.spacecodee.healthproyect.dto.users.UserDto;
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
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ArrayList;
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
        this.txtFindByDni.addEventFilter(KeyEvent.KEY_TYPED, AppUtils.numericValidation(8));
        this.txtFindByUserName.addEventFilter(KeyEvent.KEY_TYPED, AppUtils.letterValidation(120));
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
        this.tableUsers.setItems(this.loadUsers());
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
                        if (!addModalController.validateLengthPassword()) {
                            var userModel = addModalController.returnUser();
                            this.add(userModel);
                            AppUtils.closeModal(actionEvent);
                        } else {
                            AppUtils.loadModalMessage("La contraseña debe tener al menos 6 carácteres", "error");
                        }
                    } else {
                        AppUtils.loadModalMessage("Todos los datos son necesarios", "error");
                    }
                });

                stage.show();
            } else if (Users.actionCrud.equalsIgnoreCase("Edit")) {
                if (this.validateSelectedUserTable()) {
                    var addModalController = AppUtils.loadAddModal(stage, "Actualizar Usuarios");
                    addModalController.getLblTitle().setText("Actualizar Usuario".toUpperCase());

                    ///edit data
                    addModalController.setUserTable(this.userTable);
                    addModalController.sendData();

                    ////////////////////////
                    addModalController.disableSections(true);

                    /////////
                    addModalController.getBtnSave().setOnAction(actionEvent -> {
                        if (!addModalController.validateTextEdit()) {
                            var peopleModel = addModalController.returnUser().getPeople();
                            this.loadModalConfirmation("¿Estas seguro(a) que quieres editar a este usuario?",
                                    actionEvent, peopleModel);
                        } else {
                            AppUtils.loadModalMessage("Todos los datos son necesarios", "error");
                        }
                    });

                    stage.show();
                } else {
                    AppUtils.loadModalMessage("Selecciona la fila a editar", "error");
                }
            }
        }
    }

    private void add(UserDto userDto) {
        if (this.userDao.validateRepeatUsername(userDto.getUserName()) == 0) {
            if (this.addressDao.add(userDto.getPeople().getAddressDto())) {
                var idAddress = this.addressDao.returnMaxId();
                if (idAddress != 0) {
                    userDto.getPeople().getAddressDto().setIdAddress(idAddress);
                    if (this.peopleDao.add(userDto.getPeople())) {
                        var idPeople = this.peopleDao.returnMaxId();
                        if (idPeople != 0) {
                            userDto.getPeople().setIdPeople(idPeople);
                            if (this.userDao.add(userDto)) {
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
        } else {
            AppUtils.loadModalMessage("Use nombre de usuario ya existe, intenta con otro", "error");
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

    private void loadModalConfirmation(String message, ActionEvent actionEvent2, PeopleDto peopleDto) {
        var stage = new Stage();
        final ModalConfirmation modalConfirmation = AppUtils.loadModalConfirmation(stage, message);

        modalConfirmation.getLblMessage().setText(message.toUpperCase());
        Images.addImg(AppUtils.urlAlert, modalConfirmation.getIconType());
        modalConfirmation.getBtnOk().setOnAction(actionEvent -> {
            if (Users.actionCrud.equalsIgnoreCase("edit")) {
                this.edit(peopleDto, actionEvent, actionEvent2);
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
        var peopleModel = new PeopleDto(idPeople);
        if (this.peopleDao.delete(peopleModel)) {
            AppUtils.loadModalMessage("Usuario eliminado con exito", "success");
            AppUtils.closeModal(actionEvent);
        } else {
            AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde", "error");
        }

        this.loadTable();
        this.changedCrudAction("add");
    }

    private void edit(PeopleDto peopleDto, ActionEvent... actionEvent) {
        if (this.peopleDao.update(peopleDto)) {
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
                this.tableUsers.setItems(this.findUsers(dni, userName));
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

    private ObservableList<UserTable> loadUsers() {
        final ObservableList<UserTable> observableArrayList = FXCollections.observableArrayList();
        observableArrayList.clear();

        var list = this.userDao.load();
        return getUserTables(observableArrayList, list);
    }

    private ObservableList<UserTable> findUsers(String dni, String userName) {
        final ObservableList<UserTable> observableArrayList = FXCollections.observableArrayList();
        observableArrayList.clear();

        var user = new UserDto(userName, new PeopleDto(dni));

        var list = this.userDao.findValue(user);
        return getUserTables(observableArrayList, list);
    }

    private ObservableList<UserTable> getUserTables(ObservableList<UserTable> observableArrayList, ArrayList<UserDto> list) {
        for (UserDto model : list) {
            observableArrayList.add(
                    new UserTable(
                            model.getIdUser(),
                            model.getPeople().getIdPeople(),
                            model.getPeople().getName(),
                            model.getPeople().getLastname(),
                            model.getPeople().getMail(),
                            model.getPeople().getDni(),
                            model.getPeople().getPhone(),
                            model.getUserName(),
                            model.getUserRolesDto().getRoleName()
                    )
            );
        }

        return observableArrayList;
    }
}
