package com.spacecodee.healthproyect.controllers.countries;

import com.spacecodee.healthproyect.controllers.modals.ModalConfirmation;
import com.spacecodee.healthproyect.dao.address.AddressDaoImpl;
import com.spacecodee.healthproyect.dao.address.IAddressDao;
import com.spacecodee.healthproyect.dao.countries.CountryDaoImpl;
import com.spacecodee.healthproyect.dao.countries.ICountryDao;
import com.spacecodee.healthproyect.dto.address.AddressTable;
import com.spacecodee.healthproyect.dto.country.CountryConverter;
import com.spacecodee.healthproyect.model.countries.CountryModel;
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
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

public class Countries implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnDelete;

    @FXML
    private TableView<CountryModel> tableCountries;

    @FXML
    private TableColumn<CountryModel, Integer> idCountry;

    @FXML
    private TableColumn<CountryModel, String> country;

    @FXML
    private Label lblTitleForm;

    @FXML
    private TextField txtCountry;

    @FXML
    private TextField txtFindByCountry;

    private final IAddressDao addressDao = new AddressDaoImpl();
    private final ICountryDao countryDao = new CountryDaoImpl();

    @Setter
    private ComboBox<CountryModel> cbxCountry;
    @Setter
    private TableView<AddressTable> tableAddress;

    private CountryModel countryModel;
    private static String actionCrud = "add";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.initTable();
    }

    private void initTable() {
        this.idCountry.setCellValueFactory(new PropertyValueFactory<>("idCountry"));
        this.country.setCellValueFactory(new PropertyValueFactory<>("countryName"));

        this.loadTable();
    }

    private void loadTable() {
        this.tableCountries.setItems(this.countryDao.load());
    }

    @FXML
    private void addOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnAdd)) {
            if (!AppUtils.validateText(this.txtCountry)) {
                if (Countries.actionCrud.equalsIgnoreCase("add")) {
                    this.add();
                } else if (Countries.actionCrud.equalsIgnoreCase("edit")) {
                    if (this.validateSelectedAddress()) {
                        this.loadModalConfirmation("¿Estas seguro(a) que quieres editar el País");
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
    private void cancelOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnCancel)) {
            Countries.actionCrud = "add";
            this.reloadTableAndForm();
            this.changedCrudAction();
        }
    }

    @FXML
    private void deleteOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnDelete)) {
            Countries.actionCrud = "delete";

            if (this.validateSelectedAddress()) {
                this.loadModalConfirmation("¿Estas seguro(a) que quieres eliminar el País");
            } else {
                AppUtils.loadModalMessage("Selecciona la fila a eliminar", "error");
            }
        }
    }

    @FXML
    private void findByOnKeyTyped(KeyEvent event) {
        if (event.getSource().equals(this.txtFindByCountry)) {
            var countryName = this.txtFindByCountry.getText().trim().toUpperCase();

            if (countryName.isEmpty()) {
                this.loadTable();
            } else {
                this.tableCountries.setItems(this.countryDao.findByName(countryName));
            }
        }
    }

    @FXML
    private void tblRolesUserOnClick(MouseEvent event) {
        if (event.getSource().equals(this.tableCountries)) {
            if (this.validateSelectedAddress()) {
                Countries.actionCrud = "edit";
                this.countryModel = this.tableCountries.getSelectionModel().getSelectedItem();
                this.changedCrudAction();
                this.txtCountry.setText(this.countryModel.getCountryName());
            }
        }
    }

    private void loadComboCountries() {
        if (this.cbxCountry != null) {
            this.cbxCountry.getItems().clear();
            this.cbxCountry.getItems().addAll(this.countryDao.listOfCountries());
            this.cbxCountry.setConverter(new CountryConverter());
        }
    }

    private void loadTableAddress() {
        if (this.tableAddress != null)
            this.tableAddress.setItems(this.addressDao.loadTable());
    }

    private void changedCrudAction() {
        var title = (Countries.actionCrud.equalsIgnoreCase("add") ? "Agregar" : "Actualizar");
        this.lblTitleForm.setText(title);
        this.btnAdd.setText(title);
    }

    private boolean validateSelectedAddress() {
        return this.tableCountries.getSelectionModel().getSelectedItem() != null;
    }

    private void reloadTableAndForm() {
        AppUtils.clearText(this.txtCountry);
        this.loadTable();
        this.loadTableAddress();
        this.loadComboCountries();
    }

    private void loadModalConfirmation(String message) {
        var stage = new Stage();

        final ModalConfirmation modalConfirmation = AppUtils.loadModalConfirmation(stage, message);

        modalConfirmation.getLblMessage().setText(message.toUpperCase());
        Images.addImg(AppUtils.urlAlert, modalConfirmation.getIconType());
        modalConfirmation.getBtnOk().setOnAction(actionEvent -> {
            if (Countries.actionCrud.equalsIgnoreCase("edit")) {
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
        var countryName = this.txtCountry.getText().trim();
        var countryModel = new CountryModel(countryName);

        if (this.countryDao.add(countryModel)) {
            this.reloadTableAndForm();
            AppUtils.loadModalMessage("Pais Agregado", "success");
        } else {
            AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde", "error");
        }
    }

    private void edit(ActionEvent actionEvent) {
        var countryName = this.txtCountry.getText().trim();
        var countryModel = new CountryModel(this.countryModel.getIdCountry(), countryName);

        if (this.countryDao.update(countryModel)) {

            AppUtils.loadModalMessage("País actualizado", "success");
            AppUtils.closeModal(actionEvent);
        } else {
            AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde", "error");
        }

        Countries.actionCrud = "add";
        this.changedCrudAction();
        this.reloadTableAndForm();
    }

    private void delete(ActionEvent actionEvent) {
        if (this.countryDao.delete(this.countryModel)) {
            this.reloadTableAndForm();
            AppUtils.loadModalMessage("País " + this.countryModel.getCountryName()
                    + " eliminado con exito", "success");
            AppUtils.closeModal(actionEvent);
        } else {
            AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde", "error");
        }

        Countries.actionCrud = "add";
        this.changedCrudAction();
        this.reloadTableAndForm();
    }
}
