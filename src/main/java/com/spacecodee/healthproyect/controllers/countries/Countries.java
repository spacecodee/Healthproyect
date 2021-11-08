package com.spacecodee.healthproyect.controllers.countries;

import com.spacecodee.healthproyect.dao.ICrudGeneric;
import com.spacecodee.healthproyect.dao.countries.CountryDaoImpl;
import com.spacecodee.healthproyect.dao.countries.ICountryDao;
import com.spacecodee.healthproyect.model.cities.CityModel;
import com.spacecodee.healthproyect.model.countries.CountryModel;
import com.spacecodee.healthproyect.model.countries.CountryTable;
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
import java.util.ResourceBundle;

public class Countries implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnDelete;

    @FXML
    private TableView<CountryTable> tableCountries;

    @FXML
    private TableColumn<CountryTable, Integer> idCountry;

    @FXML
    private TableColumn<CountryTable, String> country;

    @FXML
    private TableColumn<CountryTable, String> city;

    @FXML
    private TableColumn<CountryTable, String> postalCode;

    @FXML
    private TableColumn<CountryTable, String> district;

    @FXML
    private TextField txtCity;

    @FXML
    private TextField txtCountry;

    @FXML
    private TextField txtDistrict;

    @FXML
    private TextField txtFindByCity;

    @FXML
    private TextField txtPostalCode;

    @FXML
    private BorderPane addressSection;

    @FXML
    private Label lblAddEdit;

    private final ICountryDao countryDao = new CountryDaoImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.initTable();
    }

    private void initTable() {
        this.idCountry.setCellValueFactory(new PropertyValueFactory<>("idCountry"));
        this.country.setCellValueFactory(new PropertyValueFactory<>("country"));
        this.city.setCellValueFactory(new PropertyValueFactory<>("cityName"));
        this.postalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        this.district.setCellValueFactory(new PropertyValueFactory<>("districtName"));

        this.loadTable();
    }

    private void loadTable() {
        this.tableCountries.setItems(this.countryDao.loadTable());
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
                this.tableCountries.setItems(this.countryDao.loadTable());
            } else {
                this.tableCountries.setItems(this.countryDao.findByNameTable(cityName));
            }
        }
    }

    @FXML
    private void tblRolesUserOnClick(MouseEvent event) {

    }
}
