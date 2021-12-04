package com.spacecodee.healthproyect.controllers.customers;

import com.spacecodee.healthproyect.controllers.modals.ModalConfirmation;
import com.spacecodee.healthproyect.dao.customers.CustomerDaoImpl;
import com.spacecodee.healthproyect.dao.customers.ICustomerDao;
import com.spacecodee.healthproyect.dao.peoples.IPeopleDao;
import com.spacecodee.healthproyect.dao.peoples.PeopleDaoImpl;
import com.spacecodee.healthproyect.converters.customer.CustomerTable;
import com.spacecodee.healthproyect.dto.customers.CustomerDto;
import com.spacecodee.healthproyect.dto.peoples.PeopleDto;
import com.spacecodee.healthproyect.utils.AppUtils;
import com.spacecodee.healthproyect.utils.Images;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

public class Customer implements Initializable {

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<CustomerTable, String> dni;

    @FXML
    private TableColumn<CustomerTable, String> email;

    @FXML
    private TableColumn<CustomerTable, Integer> idUser;

    @FXML
    private TableColumn<CustomerTable, String> lastName;

    @FXML
    private TableColumn<CustomerTable, String> name;

    @FXML
    private TableColumn<CustomerTable, String> phone;

    @FXML
    private TableColumn<CustomerTable, String> userName;

    @FXML
    private TableView<CustomerTable> tableCustomers;

    @FXML
    private TextField txtFindByDni;

    @FXML
    private TextField txtFindByUserName;

    private final ICustomerDao customerDao = new CustomerDaoImpl();
    private final IPeopleDao peopleDao = new PeopleDaoImpl();

    @Getter
    @Setter
    private CustomerTable customerTable;

    private static String actionCrud = "edit";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.txtFindByDni.addEventFilter(KeyEvent.KEY_TYPED, AppUtils.numericValidation(8));
        this.txtFindByUserName.addEventFilter(KeyEvent.KEY_TYPED, AppUtils.letterValidation(120));
        this.initTable();
    }

    private void initTable() {
        this.idUser.setCellValueFactory(new PropertyValueFactory<>("idCustomer"));
        this.name.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        this.email.setCellValueFactory(new PropertyValueFactory<>("email"));
        this.dni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        this.phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        this.phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        this.userName.setCellValueFactory(new PropertyValueFactory<>("userName"));

        this.loadTable();
    }

    private void loadTable() {
        this.tableCustomers.setItems(this.loadCustomers());
    }

    @FXML
    private void btnCancelOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnCancel)) {
            this.loadTable();
            this.changedCrudAction("Add");
        }
    }

    @FXML
    private void btnUpdateOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnUpdate)) {
            Stage stage = new Stage();
            if (Customer.actionCrud.equalsIgnoreCase("Edit")) {
                if (this.validateSelectedUserTable()) {
                    var addModalController = AppUtils.loadAddModal(stage, "Actualizar Cliente");
                    addModalController.getLblTitle().setText("Actualizar Cliente".toUpperCase());
                    ///edit data
                    addModalController.setCustomerTable(this.customerTable);
                    addModalController.sendDataCustomer();

                    ////////////////////////
                    addModalController.disableSections(true);

                    /////////
                    addModalController.getBtnSave().setOnAction(actionEvent -> {
                        if (!addModalController.validateTextEdit()) {
                            var peopleModel = addModalController.returnUser().getPeople();
                            this.loadModalConfirmation(
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

    private void loadModalConfirmation(ActionEvent actionEvent2, PeopleDto peopleDto) {
        var stage = new Stage();
        final ModalConfirmation modalConfirmation = AppUtils.loadModalConfirmation(stage, "¿Estas seguro(a) que quieres editar a este usuario?");

        modalConfirmation.getLblMessage().setText("¿Estas seguro(a) que quieres editar a este usuario?".toUpperCase());
        Images.addImg(AppUtils.urlAlert, modalConfirmation.getIconType());
        modalConfirmation.getBtnOk().setOnAction(actionEvent -> {
            if (Customer.actionCrud.equalsIgnoreCase("edit")) {
                this.edit(peopleDto, actionEvent, actionEvent2);
            }
        });
        modalConfirmation.getBtnCancel().setOnAction(actionEvent -> {
            this.changedCrudAction("Add");
            AppUtils.closeModal(actionEvent);
        });

        stage.show();
    }

    private void edit(PeopleDto peopleDto, ActionEvent... actionEvent) {
        if (this.peopleDao.update(peopleDto)) {
            AppUtils.loadModalMessage("Cliente Actualizado", "success");
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
                this.tableCustomers.setItems(this.findCustomers(dni, userName));
            }

        }
    }

    @FXML
    private void tblUsersOnClick(MouseEvent event) {
        if (event.getSource().equals(this.tableCustomers)) {
            this.changedCrudAction("edit");
            this.setCustomerTable(this.tableCustomers.getSelectionModel().getSelectedItem());
        }
    }

    private boolean validateSelectedUserTable() {
        return this.tableCustomers.getSelectionModel().getSelectedItem() != null;
    }

    private void changedCrudAction(String type) {
        Customer.actionCrud = type;
    }

    private ObservableList<CustomerTable> loadCustomers() {
        final ObservableList<CustomerTable> observableArrayList = FXCollections.observableArrayList();
        observableArrayList.clear();

        var list = this.customerDao.load();
        for (CustomerDto model : list) {
            observableArrayList.add(
                    new CustomerTable(
                            model.getIdCustomer(),
                            model.getPeople().getIdPeople(),
                            model.getPeople().getName(),
                            model.getPeople().getLastname(),
                            model.getPeople().getMail(),
                            model.getPeople().getDni(),
                            model.getPeople().getPhone(),
                            model.getUserName()
                    )
            );
        }

        return observableArrayList;
    }

    private ObservableList<CustomerTable> findCustomers(String dni, String userName) {
        final ObservableList<CustomerTable> observableArrayList = FXCollections.observableArrayList();
        observableArrayList.clear();

        var list = this.customerDao.findValue(
                new CustomerDto(userName, new PeopleDto(dni))
        );

        for (CustomerDto model : list) {
            observableArrayList.add(
                    new CustomerTable(
                            model.getIdCustomer(),
                            model.getPeople().getIdPeople(),
                            model.getPeople().getName(),
                            model.getPeople().getLastname(),
                            model.getPeople().getMail(),
                            model.getPeople().getDni(),
                            model.getPeople().getPhone(),
                            model.getUserName()
                    )
            );
        }

        return observableArrayList;
    }
}
