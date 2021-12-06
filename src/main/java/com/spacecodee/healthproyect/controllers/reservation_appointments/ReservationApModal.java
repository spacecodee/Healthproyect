package com.spacecodee.healthproyect.controllers.reservation_appointments;

import com.spacecodee.healthproyect.converters.reservation_appointments.ReservationTable;
import com.spacecodee.healthproyect.dao.ICrudGeneric;
import com.spacecodee.healthproyect.dao.cities.CityDaoImpl;
import com.spacecodee.healthproyect.dao.cities.ICityDao;
import com.spacecodee.healthproyect.dao.countries.CountryDaoImpl;
import com.spacecodee.healthproyect.dao.countries.ICountryDao;
import com.spacecodee.healthproyect.dao.customers.CustomerDaoImpl;
import com.spacecodee.healthproyect.dao.customers.ICustomerDao;
import com.spacecodee.healthproyect.dao.districs.DistrictDaoImpl;
import com.spacecodee.healthproyect.dao.districs.IDistrictDao;
import com.spacecodee.healthproyect.dao.type_reservations.TypeReservationsDaoImpl;
import com.spacecodee.healthproyect.converters.city.CityConverter;
import com.spacecodee.healthproyect.converters.country.CountryConverter;
import com.spacecodee.healthproyect.converters.district.DistrictConverter;
import com.spacecodee.healthproyect.converters.type_reservations.TypeReservationConverter;
import com.spacecodee.healthproyect.dto.address.AddressDto;
import com.spacecodee.healthproyect.dto.cities.CityDto;
import com.spacecodee.healthproyect.dto.countries.CountryDto;
import com.spacecodee.healthproyect.dto.customers.CustomerDto;
import com.spacecodee.healthproyect.dto.districts.DistrictDto;
import com.spacecodee.healthproyect.dto.peoples.PeopleDto;
import com.spacecodee.healthproyect.dto.reservation_appointments.ReservationApDto;
import com.spacecodee.healthproyect.dto.reserved_days.ReservedDaysDto;
import com.spacecodee.healthproyect.dto.type_reservations.TypeReservationDto;
import com.spacecodee.healthproyect.dto.users.UserDto;
import com.spacecodee.healthproyect.utils.AppUtils;
import com.spacecodee.healthproyect.utils.Images;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ReservationApModal implements Initializable {

    @FXML
    private ImageView iconSearch;

    @FXML
    private HBox hbSectionCustomer;

    @Getter
    @FXML
    private Button btnSave;

    @FXML
    private Button btnSearch;

    @FXML
    private ComboBox<CityDto> cbxCity;

    @FXML
    private ComboBox<CountryDto> cbxCountry;

    @FXML
    private ComboBox<DistrictDto> cbxDistrict;

    @FXML
    private ComboBox<String> cbxHours;

    @FXML
    private ComboBox<String> cbxMinutes;

    @FXML
    private ComboBox<TypeReservationDto> cbxTypeReservation;

    @FXML
    private DatePicker dtBirthDate;

    @FXML
    private DatePicker dtDateReservation;

    @FXML
    private ImageView iconSave;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtDni;

    @FXML
    private TextField txtDniCustomer;

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
    private final ICrudGeneric<TypeReservationDto> typeReservationsDao = new TypeReservationsDaoImpl();
    private final ICityDao cityDao = new CityDaoImpl();
    private final IDistrictDao districtDao = new DistrictDaoImpl();
    private final ICustomerDao customerDao = new CustomerDaoImpl();

    @Getter
    @Setter
    private ReservationTable reservationTable;
    @Getter
    private CustomerDto customerDto = null;

    ArrayList<TypeReservationDto> listTypeReservations;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.txtDni.addEventFilter(KeyEvent.KEY_TYPED, AppUtils.numericValidation(8));
        this.txtPhone.addEventFilter(KeyEvent.KEY_TYPED, AppUtils.numericValidation(9));
        Images.addImg("icons/save.png", this.iconSave);
        Images.addImg("icons/search.png", this.iconSearch);
        this.loadCountries();
        this.loadCities(0);
        this.loadDistricts(0);
        this.loadTypeReservations();
        this.loadHours();
        this.loadMinutes();
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

    @FXML
    void onSearchOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnSearch)) {
            var dni = this.txtDniCustomer.getText().trim();
            if (!dni.isEmpty()) {
                customerDto = this.customerDao.findCustomerByDni(dni);

                if (customerDto != null) {
                    AppUtils.loadModalMessage("Cliente encontrado", "success");
                    this.txtDni.setText(customerDto.getPeople().getDni());
                    this.txtName.setText(customerDto.getPeople().getName());
                    this.txtLastName.setText(customerDto.getPeople().getLastname());
                    this.txtEmail.setText(customerDto.getPeople().getMail());
                    this.txtPhone.setText(customerDto.getPeople().getPhone());
                    this.dtBirthDate.setValue(AppUtils.LOCAL_DATE(customerDto.getPeople().getBirthDate()));
                    this.txtUserName.setText(customerDto.getUserName());
                    this.hbSectionCustomer.setDisable(false);
                    this.disableSections(true);
                } else {
                    AppUtils.loadModalMessage("No existe un cliente con ese DNI", "error");
                }
            } else {
                AppUtils.loadModalMessage("Debes ingresar un DNI", "error");
            }
        }
    }

    private void loadCountries() {
        List<CountryDto> listCountries = this.countryDao.load();
        this.cbxCountry.getItems().clear();
        this.cbxCountry.getItems().addAll(listCountries);
        this.cbxCountry.setConverter(new CountryConverter());
    }

    private void loadCities(int id) {
        List<CityDto> listCities;
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
        ArrayList<DistrictDto> listDistrict;
        if (id != 0) {
            listDistrict = this.districtDao.listOfDistrict(id);
        } else {
            listDistrict = this.districtDao.load();
        }
        this.cbxDistrict.getItems().clear();
        this.cbxDistrict.getItems().addAll(listDistrict);
        this.cbxDistrict.setConverter(new DistrictConverter());
    }

    private void loadTypeReservations() {
        this.listTypeReservations = this.typeReservationsDao.load();
        this.cbxTypeReservation.getItems().clear();
        this.cbxTypeReservation.getItems().addAll(listTypeReservations);
        this.cbxTypeReservation.setConverter(new TypeReservationConverter());
    }

    private void loadMinutes() {
        final List<String> listMinutes = new ArrayList<>() {
            {
                add("00");
                add("10");
                add("20");
                add("30");
                add("40");
                add("50");
            }
        };
        this.cbxMinutes.getItems().addAll(listMinutes);
    }

    private void loadHours() {
        final List<String> listHours = new ArrayList<>() {
            {
                add("07");
                add("08");
                add("09");
                add("10");
                add("11");
                add("12");
                add("13");
                add("14");
                add("15");
                add("16");
                add("17");
                add("18");
                add("19");
                add("20");
                add("21");
                add("22");
            }
        };
        this.cbxHours.getItems().addAll(listHours);
    }

    public boolean validateText() {
        return this.validateTextEdit() || this.dtBirthDate.getValue() == null
                || this.cbxCountry.getSelectionModel().isEmpty() || this.cbxCity.getSelectionModel().isEmpty()
                || this.cbxDistrict.getSelectionModel().isEmpty() || this.txtAddress.getText().trim().isEmpty()
                || this.txtUserName.getText().trim().isEmpty();
    }

    public boolean validateTextEdit() {
        return this.cbxTypeReservation.getSelectionModel().isEmpty()
                || this.dtDateReservation.getValue() == null
                || this.cbxHours.getSelectionModel().isEmpty()
                || this.cbxMinutes.getSelectionModel().isEmpty();
    }

    private AddressDto returnAddress() {
        var addressName = this.txtAddress.getText().trim();
        var idDistrict = (this.cbxDistrict.getSelectionModel().getSelectedItem() != null)
                ? this.cbxDistrict.getSelectionModel().getSelectedItem().getIdDistrict()
                : 0;
        return new AddressDto(addressName, new DistrictDto(idDistrict));
    }

    private PeopleDto returnPeople() {
        var id = 0;
        if (this.reservationTable != null) {
            id = this.reservationTable.getIdPeople();
        }

        var dni = this.txtDni.getText().trim();
        var name = this.txtName.getText().trim();
        var lastName = this.txtLastName.getText().trim();
        var mail = this.txtEmail.getText().trim();
        var phone = this.txtPhone.getText().trim();
        var birthDate = (this.dtBirthDate.getValue() != null) ? this.dtBirthDate.getValue().toString() : "";

        return new PeopleDto(
                id, dni, name, lastName, mail, phone, birthDate, this.returnAddress()
        );
    }

    private CustomerDto returnCustomer() {
        var id = 0;
        if (this.reservationTable != null) {
            id = this.reservationTable.getIdCustomer();
        } else if (this.customerDto != null) {
            id = this.customerDto.getIdCustomer();
        }


        var userName = this.txtUserName.getText().trim();
        return new CustomerDto(id, userName, this.returnPeople());
    }

    private TypeReservationDto returnTypeReservation() {
        var idTypeReservation = this.cbxTypeReservation.getSelectionModel().getSelectedItem().getIdTypeReservation();
        return new TypeReservationDto(idTypeReservation);
    }

    private ReservedDaysDto returnReservedDays() {
        var id = 0;
        if (this.reservationTable != null) {
            id = this.reservationTable.getIdReservationDate();
        }
        var dateReservation = this.dtDateReservation.getValue().toString();
        var hours = this.cbxHours.getSelectionModel().getSelectedItem();
        var minutes = this.cbxMinutes.getSelectionModel().getSelectedItem();

        var dayReserved = dateReservation + " " + hours + ":" + minutes + ":00";
        return new ReservedDaysDto(id, dayReserved);
    }

    public ReservationApDto returnReservationAp() {
        var id = 0;
        if (this.reservationTable != null) {
            id = this.reservationTable.getIdReservationAppointment();
        }
        return new ReservationApDto(id, this.returnCustomer(), new UserDto(9),
                this.returnTypeReservation(), this.returnReservedDays());
    }

    public void disableSections(boolean disable) {
        this.txtDni.setDisable(disable);
        this.txtName.setDisable(disable);
        this.txtLastName.setDisable(disable);
        this.txtEmail.setDisable(disable);
        this.txtPhone.setDisable(disable);
        this.dtBirthDate.setDisable(disable);
        this.txtUserName.setDisable(disable);
        this.cbxCountry.setDisable(disable);
        this.cbxCity.setDisable(disable);
        this.cbxDistrict.setDisable(disable);
        this.txtAddress.setDisable(disable);
        this.hbSectionCustomer.setDisable(disable);
    }

    public void sendData() {
        //customer
        this.txtDni.setText(this.reservationTable.getCustomerDni());
        this.txtName.setText(this.reservationTable.getCustomerName());
        this.txtLastName.setText(this.reservationTable.getCustomerLastName());

        //reservation date
        var date = this.reservationTable.getDateReservation();
        var partsDate = date.split(" ");

        var partsTime = partsDate[1].split(":");
        var hours = partsTime[0];
        var minutes = partsTime[1];

        this.dtDateReservation.setValue(AppUtils.LOCAL_DATE(partsDate[0]));
        this.cbxHours.getSelectionModel().select(hours);
        this.cbxMinutes.getSelectionModel().select(minutes);

        //type reservation
        var idTypeReservation = this.getPositionDistrict(this.listTypeReservations,
                new TypeReservationDto(this.reservationTable.getIdTypeReservation()));
        this.cbxTypeReservation.getSelectionModel().select(idTypeReservation);
    }

    private int getPositionDistrict(ArrayList<TypeReservationDto> list, TypeReservationDto typeReservationDto) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getIdTypeReservation() == typeReservationDto.getIdTypeReservation()) {
                return i;
            }
        }

        return 0;
    }
}
