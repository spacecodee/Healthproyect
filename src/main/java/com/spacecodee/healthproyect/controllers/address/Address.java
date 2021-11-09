package com.spacecodee.healthproyect.controllers.address;

import com.spacecodee.healthproyect.dao.address.AddressDaoImpl;
import com.spacecodee.healthproyect.dao.address.IAddressDao;
import com.spacecodee.healthproyect.dao.cities.CityDaoImpl;
import com.spacecodee.healthproyect.dao.cities.ICityDao;
import com.spacecodee.healthproyect.dao.countries.CountryDaoImpl;
import com.spacecodee.healthproyect.dao.countries.ICountryDao;
import com.spacecodee.healthproyect.dao.districs.DistrictDaoImpl;
import com.spacecodee.healthproyect.dao.districs.IDistrictDao;
import com.spacecodee.healthproyect.dao.postal_code.IPostalCodeDao;
import com.spacecodee.healthproyect.dao.postal_code.PostalCodeDaoImpl;
import com.spacecodee.healthproyect.dto.city.CityConverter;
import com.spacecodee.healthproyect.dto.country.CountryConverter;
import com.spacecodee.healthproyect.model.cities.CityModel;
import com.spacecodee.healthproyect.model.countries.CountryModel;
import com.spacecodee.healthproyect.dto.address.AddressTable;
import com.spacecodee.healthproyect.model.districts.DistrictModel;
import com.spacecodee.healthproyect.model.postal_codes.PostalCodeModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

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
    private final IDistrictDao districtDao = new DistrictDaoImpl();
    private final IPostalCodeDao postalCodeDao = new PostalCodeDaoImpl();
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

    }

    @FXML
    private void cancelOnAction(ActionEvent event) {

    }

    @FXML
    private void deleteRolOnAction(ActionEvent event) {

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

    private void changedCrudAction() {
        var title = (Address.actionCrud.equalsIgnoreCase("add") ? "Agregar" : "Actualizar");
        this.lblAddEdit.setText(title);
        this.btnAdd.setText(title);
    }

    private boolean validateSelectedRoles() {
        return this.tableCountries.getSelectionModel().getSelectedItem() != null;
    }

    private void reloadTableAndForm() {
        this.loadTable();
        this.loadCountries();
        this.loadCities();
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
