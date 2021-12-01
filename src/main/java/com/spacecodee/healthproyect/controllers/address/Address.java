package com.spacecodee.healthproyect.controllers.address;

import com.spacecodee.healthproyect.controllers.modals.ModalConfirmation;
import com.spacecodee.healthproyect.dao.address.AddressDaoImpl;
import com.spacecodee.healthproyect.dao.address.IAddressDao;
import com.spacecodee.healthproyect.dao.cities.CityDaoImpl;
import com.spacecodee.healthproyect.dao.cities.ICityDao;
import com.spacecodee.healthproyect.dao.countries.CountryDaoImpl;
import com.spacecodee.healthproyect.dao.countries.ICountryDao;
import com.spacecodee.healthproyect.dao.districs.DistrictDaoImpl;
import com.spacecodee.healthproyect.dao.districs.IDistrictDao;
import com.spacecodee.healthproyect.converters.city.CityConverter;
import com.spacecodee.healthproyect.converters.country.CountryConverter;
import com.spacecodee.healthproyect.converters.district.DistrictConverter;
import com.spacecodee.healthproyect.dto.address.AddressDto;
import com.spacecodee.healthproyect.dto.cities.CityDto;
import com.spacecodee.healthproyect.dto.countries.CountryDto;
import com.spacecodee.healthproyect.converters.address.AddressTable;
import com.spacecodee.healthproyect.dto.districts.DistrictDto;
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

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Address implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnAddCities;

    @FXML
    private Button btnAddCountries;

    @FXML
    private Button btnAddDistrics;

    @FXML
    private Label lblAddEdit;

    @FXML
    private ComboBox<CityDto> cbxCity;

    @FXML
    private ComboBox<CountryDto> cbxCountry;

    @FXML
    private ComboBox<DistrictDto> cbxDistrict;

    @FXML
    private TableView<AddressTable> tableAddress;

    @FXML
    private TableColumn<AddressTable, Integer> idAddress;

    @FXML
    private TableColumn<AddressTable, String> country;

    @FXML
    private TableColumn<AddressTable, String> city;

    @FXML
    private TableColumn<AddressTable, String> district;

    @FXML
    private TableColumn<AddressTable, String> address;

    @FXML
    private TextField txtFindByCity;

    @FXML
    private TextField txtFindByDistrict;

    @FXML
    private TextField txtAddress;

    private final IAddressDao addressDao = new AddressDaoImpl();
    private final ICountryDao countryDao = new CountryDaoImpl();
    private final ICityDao cityDao = new CityDaoImpl();
    private final IDistrictDao districtDao = new DistrictDaoImpl();

    private AddressTable addressTable;
    private ArrayList<CityDto> listCities;
    private ArrayList<CountryDto> listCountries;
    private ArrayList<DistrictDto> listDistrict;

    private static String actionCrud = "add";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.loadCountries();
        this.loadCities(0);
        this.loadDistricts(0);
        this.initTable();
    }

    private void loadCities(int id) {
        if (id != 0) {
            this.listCities = this.cityDao.listOfCities(id);
        } else {
            this.listCities = this.cityDao.load();
        }
        this.cbxCity.getItems().clear();
        this.cbxCity.getItems().addAll(this.listCities);
        this.cbxCity.setConverter(new CityConverter());
    }

    private void loadCountries() {
        this.listCountries = this.countryDao.load();
        this.cbxCountry.getItems().clear();
        this.cbxCountry.getItems().addAll(this.listCountries);
        this.cbxCountry.setConverter(new CountryConverter());
    }

    private void loadDistricts(int id) {
        if (id != 0) {
            this.listDistrict = this.districtDao.listOfDistrict(id);
        } else {
            this.listDistrict = this.districtDao.load();
        }
        this.cbxDistrict.getItems().clear();
        this.cbxDistrict.getItems().addAll(this.listDistrict);
        this.cbxDistrict.setConverter(new DistrictConverter());
    }

    private void initTable() {
        this.idAddress.setCellValueFactory(new PropertyValueFactory<>("idAddress"));
        this.country.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        this.city.setCellValueFactory(new PropertyValueFactory<>("cityName"));
        this.district.setCellValueFactory(new PropertyValueFactory<>("districtName"));
        this.address.setCellValueFactory(new PropertyValueFactory<>("addressName"));

        this.loadTable();
    }

    private void loadTable() {
        this.tableAddress.setItems(this.loadAddress());
    }

    @FXML
    private void comboChangeOnAction(ActionEvent event) {
        if (event.getSource().equals(this.cbxCountry)) {
            this.loadCities(AppUtils.loadCities(this.cbxCountry));
        } else if (event.getSource().equals(this.cbxCity)) {
            this.loadDistricts(AppUtils.loadDistricts(this.cbxDistrict));
        }
    }

    @FXML
    private void addOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnAdd)) {
            if (!this.validateCombo(this.cbxCity, this.cbxCountry, this.cbxDistrict)
                    && !this.txtAddress.getText().trim().isEmpty()) {
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
    private void btnAddOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnAddCountries)) {
            Stage stage = new Stage();

            final var countriesController = AppUtils.loadCountriesModal(stage, "Agregar Paises");
            countriesController.setCbxCountry(this.cbxCountry);
            countriesController.setTableAddress(this.tableAddress);

            stage.show();
        } else if (event.getSource().equals(this.btnAddCities)) {
            Stage stage = new Stage();

            final var countriesController = AppUtils.loadCitiesModal(stage, "Agregar Ciudades");
            countriesController.setCbxCity(this.cbxCity);
            countriesController.setTableAddress(this.tableAddress);

            stage.show();
        } else if (event.getSource().equals(this.btnAddDistrics)) {
            Stage stage = new Stage();

            final var districtsController = AppUtils.loadDistrictsModal(stage, "Agregar Distritos");
            districtsController.setCbxCity(this.cbxCity);
            districtsController.setCbxCountry(this.cbxCountry);
            districtsController.setTableAddress(this.tableAddress);

            stage.show();
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
    private void findByOnTyped(KeyEvent event) {
        if (event.getSource().equals(this.txtFindByCity) || event.getSource().equals(this.txtFindByDistrict)) {
            var cityName = this.txtFindByCity.getText().trim();
            var districtName = this.txtFindByDistrict.getText().trim();

            if (cityName.isEmpty()) {
                this.loadTable();
            } else {
                this.tableAddress.setItems(this.findAddress(districtName, cityName));
            }
        }
    }

    @FXML
    private void tblAddressOnClick(MouseEvent event) {
        if (event.getSource().equals(this.tableAddress)) {
            Address.actionCrud = "edit";
            this.addressTable = this.tableAddress.getSelectionModel().getSelectedItem();
            if (this.addressTable != null) {
                this.changedCrudAction();
                var city = new CityDto(this.addressTable.getCityName());
                var positionCity = this.getPositionCity(this.listCities, city);
                this.cbxCity.getSelectionModel().select(positionCity);

                var country = new CountryDto(this.addressTable.getCountryName());
                var positionCountry = this.getPositionCountry(this.listCountries, country);
                this.cbxCountry.getSelectionModel().select(positionCountry);

                var district = new DistrictDto(this.addressTable.getDistrictName());
                var positionDistrict = this.getPositionDistrict(this.listDistrict, district);
                this.cbxDistrict.getSelectionModel().select(positionDistrict);

                var address = this.addressTable.getAddressName();
                this.txtAddress.setText(address);
            }
        }
    }

    private void add() {
        AddressDto address = returnAddress();
        var validation = false;
        var addressList = this.tableAddress.getItems();

        for (AddressTable table : addressList) {
            if (table.getAddressName().equalsIgnoreCase(address.getAddressName())
                    && table.getDistrictName().equalsIgnoreCase(address.getDistrictDto().getDistrictName())) {
                validation = true;
                break;
            }
        }

        if (!validation) {
            if (this.addressDao.add(address)) {
                AppUtils.loadModalMessage("Dirección agregada", "success");
            } else {
                AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde", "error");
            }
        } else {
            AppUtils.loadModalMessage("Esa dirección ya existe", "error");
        }

        this.reloadTableAndForm();
    }

    private void edit(ActionEvent actionEvent) {
        AddressDto address = returnAddress();
        var validation = false;
        var addressList = this.tableAddress.getItems();

        for (AddressTable table : addressList) {
            if (table.getAddressName().equalsIgnoreCase(address.getAddressName())
                    && table.getIdAddress() != address.getIdAddress()
                    && table.getDistrictName().equalsIgnoreCase(address.getDistrictDto().getDistrictName())) {
                validation = true;
                break;
            }
        }

        if (!validation) {
            if (this.addressDao.update(address)) {
                AppUtils.loadModalMessage("Dirección actualizada", "success");
            } else {
                AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde", "error");
            }
        } else {
            AppUtils.loadModalMessage("Esa dirección ya existe", "error");
        }

        Address.actionCrud = "add";
        this.changedCrudAction();
        this.reloadTableAndForm();
        AppUtils.closeModal(actionEvent);
    }

    private AddressDto returnAddress() {
        var idAddress = 0;
        if (this.validateSelectedAddress()) {
            idAddress = this.tableAddress.getSelectionModel().getSelectedItem().getIdAddress();
        }
        var addressName = this.txtAddress.getText().trim();
        var idDistrict = this.cbxDistrict.getSelectionModel().getSelectedItem().getIdDistrict();
        var districtName = this.cbxDistrict.getSelectionModel().getSelectedItem().getDistrictName();

        return new AddressDto(
                idAddress,
                addressName,
                new DistrictDto(idDistrict, districtName)
        );
    }

    private void delete(ActionEvent actionEvent) {
        var address = new AddressDto(this.addressTable.getIdAddress());

        if (this.addressDao.delete(address)) {
            AppUtils.loadModalMessage("Dirección eliminada con exito", "success");
            AppUtils.closeModal(actionEvent);
        } else {
            AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde", "error");
        }

        Address.actionCrud = "add";
        this.changedCrudAction();
        this.reloadTableAndForm();
    }

    private void loadModalConfirmation(String message) {
        var stage = new Stage();
        final ModalConfirmation modalConfirmation = AppUtils.loadModalConfirmation(stage, message);

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
            Address.actionCrud = "add";
            this.changedCrudAction();
            this.reloadTableAndForm();
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
        return this.tableAddress.getSelectionModel().getSelectedItem() != null;
    }

    private void reloadTableAndForm() {
        AppUtils.clearText(this.txtAddress);
        this.loadTable();
        this.cbxCountry.getSelectionModel().select(0);
        this.cbxCity.getSelectionModel().select(0);
        this.cbxDistrict.getSelectionModel().select(0);
    }

    private boolean validateCombo(ComboBox<CityDto> cbxCity,
                                  ComboBox<CountryDto> cbxCountry,
                                  ComboBox<DistrictDto> cbxDistrict) {
        return cbxCity.getSelectionModel().isEmpty()
                || cbxCountry.getSelectionModel().isEmpty()
                || cbxDistrict.getSelectionModel().isEmpty();
    }

    private int getPositionCity(ArrayList<CityDto> listCities, CityDto cityDto) {
        for (int i = 0; i < listCities.size(); i++) {
            if (listCities.get(i).getCityName().equalsIgnoreCase(cityDto.getCityName())) {
                return i;
            }
        }

        return 0;
    }

    private int getPositionCountry(ArrayList<CountryDto> listCountries, CountryDto countryDto) {
        for (int i = 0; i < listCountries.size(); i++) {
            if (listCountries.get(i).getCountryName().equalsIgnoreCase(countryDto.getCountryName())) {
                return i;
            }
        }

        return 0;
    }

    private int getPositionDistrict(ArrayList<DistrictDto> listDistrict, DistrictDto districtDto) {
        for (int i = 0; i < listDistrict.size(); i++) {
            if (listDistrict.get(i).getDistrictName().equalsIgnoreCase(districtDto.getDistrictName())) {
                return i;
            }
        }

        return 0;
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

    private ObservableList<AddressTable> findAddress(String districtName, String cityName) {
        final ObservableList<AddressTable> observableArrayList = FXCollections.observableArrayList();
        observableArrayList.clear();
        var address = new AddressDto(new DistrictDto(districtName, new CityDto(cityName)));
        var list = this.addressDao.findValue(address);
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
