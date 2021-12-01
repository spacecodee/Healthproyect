package com.spacecodee.healthproyect.controllers.countries;

import com.spacecodee.healthproyect.controllers.modals.ModalConfirmation;
import com.spacecodee.healthproyect.dao.address.AddressDaoImpl;
import com.spacecodee.healthproyect.dao.address.IAddressDao;
import com.spacecodee.healthproyect.dao.countries.CountryDaoImpl;
import com.spacecodee.healthproyect.dao.countries.ICountryDao;
import com.spacecodee.healthproyect.converters.address.AddressTable;
import com.spacecodee.healthproyect.converters.country.CountryConverter;
import com.spacecodee.healthproyect.dto.address.AddressDto;
import com.spacecodee.healthproyect.dto.countries.CountryDto;
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
    private TableView<CountryDto> tableCountries;

    @FXML
    private TableColumn<CountryDto, Integer> idCountry;

    @FXML
    private TableColumn<CountryDto, String> country;

    @FXML
    private Label lblTitleForm;

    @FXML
    private TextField txtCountry;

    @FXML
    private TextField txtFindByCountry;

    private final IAddressDao addressDao = new AddressDaoImpl();
    private final ICountryDao countryDao = new CountryDaoImpl();

    @Setter
    private ComboBox<CountryDto> cbxCountry;
    @Setter
    private TableView<AddressTable> tableAddress;

    private CountryDto countryDto;
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
        this.tableCountries.setItems(this.loadCountries());
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
                this.tableCountries.setItems(this.findCountries(countryName));
            }
        }
    }

    @FXML
    private void tblRolesUserOnClick(MouseEvent event) {
        if (event.getSource().equals(this.tableCountries)) {
            if (this.validateSelectedAddress()) {
                Countries.actionCrud = "edit";
                this.countryDto = this.tableCountries.getSelectionModel().getSelectedItem();
                this.changedCrudAction();
                this.txtCountry.setText(this.countryDto.getCountryName());
            }
        }
    }

    private void loadComboCountries() {
        if (this.cbxCountry != null) {
            this.cbxCountry.getItems().clear();
            this.cbxCountry.getItems().addAll(this.countryDao.load());
            this.cbxCountry.setConverter(new CountryConverter());
        }
    }

    private void loadTableAddress() {
        if (this.tableAddress != null)
            this.tableAddress.setItems(this.loadAddress());
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
            Countries.actionCrud = "add";
            this.changedCrudAction();
            this.reloadTableAndForm();
            AppUtils.closeModal(actionEvent);
        });

        stage.show();
    }

    private void add() {
        var countryName = this.txtCountry.getText().trim();
        var countryModel = new CountryDto(countryName);

        if (this.countryDao.add(countryModel)) {
            AppUtils.loadModalMessage("Pais Agregado", "success");
        } else {
            AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde", "error");
        }
        this.reloadTableAndForm();
    }

    private void edit(ActionEvent actionEvent) {
        var countryName = this.txtCountry.getText().trim();
        var countryModel = new CountryDto(this.countryDto.getIdCountry(), countryName);

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
        if (this.countryDao.delete(this.countryDto)) {
            AppUtils.loadModalMessage("País " + this.countryDto.getCountryName()
                    + " eliminado con exito", "success");
            AppUtils.closeModal(actionEvent);
        } else {
            AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde", "error");
        }

        Countries.actionCrud = "add";
        this.changedCrudAction();
        this.reloadTableAndForm();
    }

    private ObservableList<CountryDto> loadCountries() {
        final ObservableList<CountryDto> observableArrayList = FXCollections.observableArrayList();
        observableArrayList.clear();
        observableArrayList.addAll(this.countryDao.load());

        return observableArrayList;
    }

    private ObservableList<CountryDto> findCountries(String name) {
        final ObservableList<CountryDto> observableArrayList = FXCollections.observableArrayList();
        observableArrayList.clear();
        observableArrayList.addAll(this.countryDao.findValue(new CountryDto(name)));

        return observableArrayList;
    }

    private ObservableList<AddressTable> loadAddress() {
        final ObservableList<AddressTable> observableArrayList = FXCollections.observableArrayList();
        observableArrayList.clear();

        var list = this.addressDao.load();
        for (AddressDto model : list) {
            observableArrayList.add(
                    new AddressTable(
                            model.getIdAddress(),
                            model.getDistrictDto().getCityDto().getCountryDto().getCountryName(),
                            model.getDistrictDto().getCityDto().getCityName(),
                            model.getDistrictDto().getDistrictName(),
                            model.getAddressName()
                    )
            );
        }

        return observableArrayList;
    }
}
