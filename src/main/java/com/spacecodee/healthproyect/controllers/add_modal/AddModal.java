package com.spacecodee.healthproyect.controllers.add_modal;

import com.spacecodee.healthproyect.dao.cities.CityDaoImpl;
import com.spacecodee.healthproyect.dao.cities.ICityDao;
import com.spacecodee.healthproyect.dao.countries.CountryDaoImpl;
import com.spacecodee.healthproyect.dao.countries.ICountryDao;
import com.spacecodee.healthproyect.dao.districs.DistrictDaoImpl;
import com.spacecodee.healthproyect.dao.districs.IDistrictDao;
import com.spacecodee.healthproyect.dao.user_roles.IUserRolesDao;
import com.spacecodee.healthproyect.dao.user_roles.UserRolesDaoImpl;
import com.spacecodee.healthproyect.dto.city.CityConverter;
import com.spacecodee.healthproyect.dto.country.CountryConverter;
import com.spacecodee.healthproyect.dto.customer.CustomerTable;
import com.spacecodee.healthproyect.dto.district.DistrictConverter;
import com.spacecodee.healthproyect.dto.user.UserTable;
import com.spacecodee.healthproyect.model.address.AddressModel;
import com.spacecodee.healthproyect.model.cities.CityModel;
import com.spacecodee.healthproyect.model.countries.CountryModel;
import com.spacecodee.healthproyect.model.customers.CustomerModel;
import com.spacecodee.healthproyect.model.districts.DistrictModel;
import com.spacecodee.healthproyect.model.peoples.PeopleModel;
import com.spacecodee.healthproyect.model.users.UserModel;
import com.spacecodee.healthproyect.model.users_roles.UserRolesModel;
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

public class AddModal implements Initializable {

    @Getter
    @FXML
    private Button btnSave;

    @Getter
    @FXML
    private ComboBox<CityModel> cbxCity;

    @Getter
    @FXML
    private ComboBox<CountryModel> cbxCountry;

    @Getter
    @FXML
    private ComboBox<DistrictModel> cbxDistrict;

    @Getter
    @FXML
    private ComboBox<UserRolesModel> cbxRol;

    @Getter
    @FXML
    private DatePicker dtBirthDate;

    @FXML
    private ImageView iconSave;

    @Getter
    @FXML
    private Label lblTitle;

    @Getter
    @FXML
    private TextField txtAddress;

    @Getter
    @FXML
    private TextField txtDni;

    @Getter
    @FXML
    private TextField txtEmail;

    @Getter
    @FXML
    private TextField txtLastName;

    @Getter
    @FXML
    private TextField txtName;

    @Getter
    @FXML
    private PasswordField txtPassword;

    @Getter
    @FXML
    private TextField txtPhone;

    @Getter
    @FXML
    private TextField txtUserName;

    private final IUserRolesDao userRolesDao = new UserRolesDaoImpl();
    private final ICountryDao countryDao = new CountryDaoImpl();
    private final ICityDao cityDao = new CityDaoImpl();
    private final IDistrictDao districtDao = new DistrictDaoImpl();

    @Getter
    @Setter
    private UserTable userTable;
    @Getter
    @Setter
    private CustomerTable customerTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.txtDni.addEventFilter(KeyEvent.KEY_TYPED, AppUtils.numericValidation(8));
        this.txtPhone.addEventFilter(KeyEvent.KEY_TYPED, AppUtils.numericValidation(9));
        Images.addImg("icons/save.png", this.iconSave);
        this.loadUserRoles();
        this.loadCountries();
        this.loadCities(0);
        this.loadDistricts(0);
    }

    private void loadCountries() {
        ArrayList<CountryModel> listCountries = this.countryDao.listOfCountries();
        this.cbxCountry.getItems().clear();
        this.cbxCountry.getItems().addAll(listCountries);
        this.cbxCountry.setConverter(new CountryConverter());
    }

    private void loadCities(int id) {
        ArrayList<CityModel> listCities;
        if (id != 0) {
            listCities = this.cityDao.listOfCities(id);
        } else {
            listCities = this.cityDao.listOfCities();
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
            listDistrict = this.districtDao.listOfDistrict();
        }
        this.cbxDistrict.getItems().clear();
        this.cbxDistrict.getItems().addAll(listDistrict);
        this.cbxDistrict.setConverter(new DistrictConverter());
    }

    private void loadUserRoles() {
        ArrayList<UserRolesModel> listUserRoles = this.userRolesDao.listOfUserRoles();
        this.cbxRol.getItems().clear();
        this.cbxRol.getItems().addAll(listUserRoles);
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

    public boolean validateLengthPassword() {
        return this.txtPassword.getText().trim().length() < 6;
    }

    public boolean validateText() {
        return this.validateTextEdit() || this.dtBirthDate.getValue() == null
                || this.cbxCountry.getSelectionModel().isEmpty() || this.cbxCity.getSelectionModel().isEmpty()
                || this.cbxDistrict.getSelectionModel().isEmpty() || this.txtAddress.getText().trim().isEmpty()
                || this.txtUserName.getText().trim().isEmpty();
    }

    public boolean validateTextFieldsUser() {
        return this.validateText() || this.txtPassword.getText().trim().isEmpty()
                || this.cbxRol.getSelectionModel().isEmpty();
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

    private UserRolesModel returnUserRol() {
        var idUserRol = (this.cbxRol.getSelectionModel().getSelectedItem() != null)
                ? this.cbxRol.getSelectionModel().getSelectedItem().getIdRolUser()
                : 0;
        return new UserRolesModel(idUserRol);
    }

    private PeopleModel returnPeople() {
        var id = 0;
        if (this.userTable != null) {
            id = this.userTable.getIdPeople();
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

    public UserModel returnUser() {
        var id = 0;
        if (this.userTable != null) {
            id = this.userTable.getIdUser();
        }
        var userName = this.txtUserName.getText().trim();
        var password = this.txtPassword.getText().trim();
        return new UserModel(id, userName, password, this.returnPeople(), this.returnUserRol());
    }

    public CustomerModel returnCustomer() {
        var id = 0;
        if (this.userTable != null) {
            id = this.userTable.getIdUser();
        }
        var userName = this.txtUserName.getText().trim();
        return new CustomerModel(id, userName, this.returnPeople());
    }

    public void disableSections(boolean disable) {
        this.txtPassword.setDisable(disable);
        this.cbxCountry.setDisable(disable);
        this.cbxCity.setDisable(disable);
        this.cbxDistrict.setDisable(disable);
        this.cbxRol.setDisable(disable);
        this.dtBirthDate.setDisable(disable);
        this.txtUserName.setDisable(disable);
        this.txtAddress.setDisable(disable);
    }

    public void sendData() {
        this.txtDni.setText(this.userTable.getDni());
        this.txtName.setText(this.userTable.getName());
        this.txtLastName.setText(this.userTable.getLastName());
        this.txtEmail.setText(this.userTable.getEmail());
        this.txtPhone.setText(this.userTable.getPhone());
    }
}
