package com.spacecodee.healthproyect.controllers.address;

import com.spacecodee.healthproyect.controllers.modals.ModalConfirmation;
import com.spacecodee.healthproyect.dao.address.AddressDaoImpl;
import com.spacecodee.healthproyect.dao.address.IAddressDao;
import com.spacecodee.healthproyect.dao.cities.CityDaoImpl;
import com.spacecodee.healthproyect.dao.cities.ICityDao;
import com.spacecodee.healthproyect.dao.countries.CountryDaoImpl;
import com.spacecodee.healthproyect.dao.countries.ICountryDao;
import com.spacecodee.healthproyect.dto.city.CityConverter;
import com.spacecodee.healthproyect.dto.country.CountryConverter;
import com.spacecodee.healthproyect.model.address.AddressModel;
import com.spacecodee.healthproyect.model.cities.CityModel;
import com.spacecodee.healthproyect.model.countries.CountryModel;
import com.spacecodee.healthproyect.dto.address.AddressTable;
import com.spacecodee.healthproyect.model.districts.DistrictModel;
import com.spacecodee.healthproyect.model.postal_codes.PostalCodeModel;
import com.spacecodee.healthproyect.utils.AppUtils;
import com.spacecodee.healthproyect.utils.Images;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Address implements Initializable {

    @FXML
    private BorderPane addressSection;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnAddCity;

    @FXML
    private Button btnAddCountry;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnDelete;

    @FXML
    private Label lblAddEdit;

    @FXML
    private ComboBox<CityModel> cbxCity;

    @FXML
    private ComboBox<CountryModel> cbxCountry;

    @FXML
    private TableView<AddressTable> tableCountries;

    @FXML
    private TableColumn<AddressTable, Integer> idAddress;

    @FXML
    private TableColumn<AddressTable, String> country;

    @FXML
    private TableColumn<AddressTable, String> city;

    @FXML
    private TableColumn<AddressTable, String> district;

    @FXML
    private TableColumn<AddressTable, String> postalCode;

    @FXML
    private TextField txtFindByCity;

    @FXML
    private TextField txtFindByDistrict;

    private final IAddressDao addressDao = new AddressDaoImpl();
    private final ICountryDao countryDao = new CountryDaoImpl();
    private final ICityDao cityDao = new CityDaoImpl();
    private AddressTable addressTable;
    private ArrayList<CityModel> listCities;
    private ArrayList<CountryModel> listCountries;

    private static String actionCrud = "add";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.loadCities();
        this.loadCountries();
        this.initTable();
    }

    private void loadCities() {
        this.listCities = this.cityDao.listOfCities();
        this.cbxCity.getItems().removeAll();
        this.cbxCity.getItems().addAll(this.listCities);
        this.cbxCity.setConverter(new CityConverter());
    }

    private void loadCountries() {
        this.listCountries = this.countryDao.listOfCountries();
        this.cbxCountry.getItems().addAll(this.listCountries);
        this.cbxCountry.setConverter(new CountryConverter());
    }

    private void initTable() {
        this.idAddress.setCellValueFactory(new PropertyValueFactory<>("idAddress"));
        this.country.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        this.city.setCellValueFactory(new PropertyValueFactory<>("cityName"));
        this.postalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        this.district.setCellValueFactory(new PropertyValueFactory<>("districtName"));

        this.loadTable();
    }

    private void loadTable() {
        this.tableCountries.setItems(this.addressDao.loadTable());
    }

    @FXML
    private void addCityOnAction(ActionEvent event) {

    }

    @FXML
    private void addCountryOnAction(ActionEvent event) {

    }

    @FXML
    private void addOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnAdd)) {
            if (!AppUtils.validateCombo(this.cbxCity, this.cbxCountry)) {
                if (Address.actionCrud.equalsIgnoreCase("add")) {
                    this.add();
                } else if (Address.actionCrud.equalsIgnoreCase("edit")) {
                    if (this.validateSelectedAddress()) {
                        this.loadModalConfirmation("¿Estas seguro(a) que quieres editar la dirección");
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
            this.reloadTableAndForm();
            Address.actionCrud = "add";
            this.changedCrudAction();
        }
    }

    @FXML
    private void deleteRolOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnDelete)) {
            Address.actionCrud = "delete";

            if (this.validateSelectedAddress()) {
                this.loadModalConfirmation("¿Estas seguro(a) que quieres eliminar esta dirección");
            } else {
                AppUtils.loadModalMessage("Selecciona la fila a eliminar", "error");
            }
        }
    }

    @FXML
    private void findByCityKeyTyped(KeyEvent event) {
        if (event.getSource().equals(this.txtFindByCity)) {
            var cityName = this.txtFindByCity.getText().trim();

            if (cityName.isEmpty()) {
                this.loadTable();
            } else {
                this.tableCountries.setItems(this.addressDao.findByNameTable(cityName));
            }
        }
    }

    @FXML
    private void findByDistrictKeyTyped(KeyEvent event) {

    }

    @FXML
    private void tblAddressOnClick(MouseEvent event) {
        if (event.getSource().equals(this.tableCountries)) {
            Address.actionCrud = "edit";
            this.addressTable = this.tableCountries.getSelectionModel().getSelectedItem();
            if (this.addressTable != null) {
                this.changedCrudAction();
                var city = new CityModel(
                        this.addressTable.getIdCity(), this.addressTable.getCityName(),
                        new PostalCodeModel(this.addressTable.getIdPostalCode(), this.addressTable.getPostalCode()),
                        new DistrictModel(this.addressTable.getIdDistrict(), this.addressTable.getDistrictName())
                );
                var positionCity = this.getPositionCity(this.listCities, city);
                this.cbxCity.getSelectionModel().select(positionCity);

                var country = new CountryModel(this.addressTable.getIdCountry(), this.addressTable.getCountryName());
                var positionCountry = this.getPositionCountry(this.listCountries, country);
                this.cbxCountry.getSelectionModel().select(positionCountry);
            }
        }
    }

    private void add() {
        var idCountry = this.cbxCountry.getSelectionModel().getSelectedItem().getIdCountry();
        var idCity = this.cbxCity.getSelectionModel().getSelectedItem().getIdCity();

        var address = new AddressModel(
                new CityModel(idCity),
                new CountryModel(idCountry)
        );

        if (this.addressDao.add(address)) {
            this.reloadTableAndForm();
            AppUtils.loadModalMessage("Dirección agregada", "success");
        } else {
            AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde", "error");
        }
    }

    private void edit(ActionEvent actionEvent) {
        var idCountry = this.cbxCountry.getSelectionModel().getSelectedItem().getIdCountry();
        var idCity = this.cbxCity.getSelectionModel().getSelectedItem().getIdCity();

        var address = new AddressModel(
                new CityModel(idCity),
                new CountryModel(idCountry)
        );

        if (this.addressDao.update(address)) {
            this.reloadTableAndForm();
            AppUtils.loadModalMessage("Dirección actualizada", "success");
            AppUtils.closeModal(actionEvent);
        } else {
            AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde", "error");
        }

        Address.actionCrud = "add";
        this.changedCrudAction();
    }

    private void delete(ActionEvent actionEvent) {
        var address = new AddressModel(this.addressTable.getIdAddress());

        if (this.addressDao.delete(address)) {
            this.reloadTableAndForm();
            AppUtils.loadModalMessage("Dirección eliminada con exito", "success");
            AppUtils.closeModal(actionEvent);
        } else {
            AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde", "error");
        }

        Address.actionCrud = "add";
        this.changedCrudAction();
    }

    private void loadModalConfirmation(String message) {
        var stage = new Stage();

        var fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(AppUtils.class.getResource(AppUtils.URL + "modals/modal-confirmation.fxml"));

        AppUtils.globalModal(stage, fxmlLoader);

        final ModalConfirmation modalConfirmation = fxmlLoader.getController();

        modalConfirmation.getLblMessage().setText(message.toUpperCase());
        Images.addImg(AppUtils.urlAlert, modalConfirmation.getIconType());
        modalConfirmation.getBtnOk().setOnAction(actionEvent -> {
            if (Address.actionCrud.equalsIgnoreCase("edit")) {
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

    private void changedCrudAction() {
        var title = (Address.actionCrud.equalsIgnoreCase("add") ? "Agregar" : "Actualizar");
        this.lblAddEdit.setText(title);
        this.btnAdd.setText(title);
    }

    private boolean validateSelectedAddress() {
        return this.tableCountries.getSelectionModel().getSelectedItem() != null;
    }

    private void reloadTableAndForm() {
        this.loadTable();
        this.cbxCountry.getSelectionModel().select(0);
        this.cbxCity.getSelectionModel().select(0);
    }

    private int getPositionCity(ArrayList<CityModel> listCities, CityModel cityModel) {
        for (int i = 0; i < listCities.size(); i++) {
            if (listCities.get(i).getDistrictModel().getDistrictName()
                    .equalsIgnoreCase(cityModel.getDistrictModel().getDistrictName())) {
                return i;
            }
        }

        return 0;
    }

    private int getPositionCountry(ArrayList<CountryModel> listCountries, CountryModel countryModel) {
        for (int i = 0; i < listCountries.size(); i++) {
            if (listCountries.get(i).getCountry().equalsIgnoreCase(countryModel.getCountry())) {
                return i;
            }
        }

        return 0;
    }
}
