package com.spacecodee.healthproyect.controllers.reservation_appointments;

import com.spacecodee.healthproyect.dao.cities.CityDaoImpl;
import com.spacecodee.healthproyect.dao.cities.ICityDao;
import com.spacecodee.healthproyect.dao.countries.CountryDaoImpl;
import com.spacecodee.healthproyect.dao.countries.ICountryDao;
import com.spacecodee.healthproyect.dao.districs.DistrictDaoImpl;
import com.spacecodee.healthproyect.dao.districs.IDistrictDao;
import com.spacecodee.healthproyect.dto.city.CityConverter;
import com.spacecodee.healthproyect.dto.country.CountryConverter;
import com.spacecodee.healthproyect.dto.customer.CustomerTable;
import com.spacecodee.healthproyect.dto.district.DistrictConverter;
import com.spacecodee.healthproyect.model.address.AddressModel;
import com.spacecodee.healthproyect.model.cities.CityModel;
import com.spacecodee.healthproyect.model.countries.CountryModel;
import com.spacecodee.healthproyect.model.customers.CustomerModel;
import com.spacecodee.healthproyect.model.districts.DistrictModel;
import com.spacecodee.healthproyect.model.peoples.PeopleModel;
import com.spacecodee.healthproyect.model.type_reservations.TypeReservationModel;
import com.spacecodee.healthproyect.utils.AppUtils;
import com.spacecodee.healthproyect.utils.Images;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReservationApModal implements Initializable {

    @FXML
    private Button btnSave;

    @FXML
    private Button btnSearchUser;

    @FXML
    private ComboBox<CityModel> cbxCity;

    @FXML
    private ComboBox<CountryModel> cbxCountry;

    @FXML
    private ComboBox<DistrictModel> cbxDistrict;

    @FXML
    private ComboBox<String> cbxHours;

    @FXML
    private ComboBox<String> cbxMinutes;

    @FXML
    private ComboBox<TypeReservationModel> cbxTypeReservation;

    @FXML
    private DatePicker dtBirthDate;

    @FXML
    private DatePicker dtDateReservation;

    @FXML
    private ImageView iconSave;

    @FXML
    private ImageView iconSearch;

    @FXML
    private Label lblTitle;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtDni;

    @FXML
    private TextField txtDniUser;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtUserName;

    private final ICountryDao countryDao = new CountryDaoImpl();
    private final ICityDao cityDao = new CityDaoImpl();
    private final IDistrictDao districtDao = new DistrictDaoImpl();

    @Getter
    @Setter
    private CustomerTable customerTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.txtDni.addEventFilter(KeyEvent.KEY_TYPED, AppUtils.numericValidation(8));
        this.txtDniUser.addEventFilter(KeyEvent.KEY_TYPED, AppUtils.numericValidation(8));
        this.txtPhone.addEventFilter(KeyEvent.KEY_TYPED, AppUtils.numericValidation(9));
        Images.addImg("icons/save.png", this.iconSave);
        Images.addImg("icons/search.png", this.iconSearch);
        this.loadCountries();
        this.loadCities(0);
        this.loadDistricts(0);
    }

    @FXML
    private void comboChangeOnAction(ActionEvent event) {
        if (event.getSource().equals(this.cbxCountry)) {
            if (!this.cbxCountry.getSelectionModel().isEmpty()) {
                var countryId = this.cbxCountry.getSelectionModel().getSelectedItem().getIdCountry();
                this.loadCities(countryId);
            }
        } else if (event.getSource().equals(this.cbxCity)) {
            if (!this.cbxCity.getSelectionModel().isEmpty()) {
                var cityId = this.cbxCity.getSelectionModel().getSelectedItem().getIdCity();
                this.loadDistricts(cityId);
            }
        }
    }

    private void loadCountries() {
        ArrayList<CountryModel> listCountries = this.countryDao.load();
        this.cbxCountry.getItems().clear();
        this.cbxCountry.getItems().addAll(listCountries);
        this.cbxCountry.setConverter(new CountryConverter());
    }

    private void loadCities(int id) {
        ArrayList<CityModel> listCities;
        if (id != 0) {
            listCities = this.cityDao.listOfCities(id);
        } else {
            listCities = this.cityDao.load();
        }
        this.cbxCity.getItems().clear();
        this.cbxCity.getItems().addAll(listCities);
        this.cbxCity.setConverter(new CityConverter());
    }

    private void loadDistricts(int id) {
        ArrayList<DistrictModel> listDistrict;
        if (id != 0) {
            listDistrict = this.districtDao.listOfDistrict(id);
        } else {
            listDistrict = this.districtDao.load();
        }
        this.cbxDistrict.getItems().clear();
        this.cbxDistrict.getItems().addAll(listDistrict);
        this.cbxDistrict.setConverter(new DistrictConverter());
    }

    public boolean validateText() {
        return this.validateTextEdit() || this.dtBirthDate.getValue() == null
                || this.cbxCountry.getSelectionModel().isEmpty() || this.cbxCity.getSelectionModel().isEmpty()
                || this.cbxDistrict.getSelectionModel().isEmpty() || this.txtAddress.getText().trim().isEmpty()
                || this.txtUserName.getText().trim().isEmpty();
    }

    public boolean validateTextEdit() {
        return this.txtDni.getText().trim().isEmpty()
                || this.txtName.getText().trim().isEmpty()
                || this.txtLastName.getText().trim().isEmpty()
                || this.txtEmail.getText().trim().isEmpty()
                || this.txtPhone.getText().trim().isEmpty();
    }

    private AddressModel returnAddress() {
        var addressName = this.txtAddress.getText().trim();
        var idDistrict = (this.cbxDistrict.getSelectionModel().getSelectedItem() != null)
                ? this.cbxDistrict.getSelectionModel().getSelectedItem().getIdDistrict()
                : 0;
        return new AddressModel(addressName, new DistrictModel(idDistrict));
    }

    private PeopleModel returnPeople() {
        var id = 0;
        if (this.customerTable != null) {
            id = this.customerTable.getIdPeople();
        }

        var dni = this.txtDni.getText().trim();
        var name = this.txtName.getText().trim();
        var lastName = this.txtLastName.getText().trim();
        var mail = this.txtEmail.getText().trim();
        var phone = this.txtPhone.getText().trim();
        var birthDate = (this.dtBirthDate.getValue() != null) ? this.dtBirthDate.getValue().toString() : "";

        return new PeopleModel(
                id, dni, name, lastName, mail, phone, birthDate, this.returnAddress()
        );
    }

    public CustomerModel returnCustomer() {
        var id = 0;
        if (this.customerTable != null) {
            id = this.customerTable.getIdCustomer();
        }
        var userName = this.txtUserName.getText().trim();
        return new CustomerModel(id, userName, this.returnPeople());
    }

}
