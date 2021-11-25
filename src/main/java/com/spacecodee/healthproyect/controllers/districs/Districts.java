package com.spacecodee.healthproyect.controllers.districs;

import com.spacecodee.healthproyect.controllers.modals.ModalConfirmation;
import com.spacecodee.healthproyect.dao.address.AddressDaoImpl;
import com.spacecodee.healthproyect.dao.address.IAddressDao;
import com.spacecodee.healthproyect.dao.cities.CityDaoImpl;
import com.spacecodee.healthproyect.dao.cities.ICityDao;
import com.spacecodee.healthproyect.dao.countries.CountryDaoImpl;
import com.spacecodee.healthproyect.dao.countries.ICountryDao;
import com.spacecodee.healthproyect.dao.districs.DistrictDaoImpl;
import com.spacecodee.healthproyect.dao.districs.IDistrictDao;
import com.spacecodee.healthproyect.dto.address.AddressTable;
import com.spacecodee.healthproyect.dto.city.CityConverter;
import com.spacecodee.healthproyect.dto.country.CountryConverter;
import com.spacecodee.healthproyect.dto.district.DistrictTable;
import com.spacecodee.healthproyect.model.cities.CityModel;
import com.spacecodee.healthproyect.model.countries.CountryModel;
import com.spacecodee.healthproyect.model.districts.DistrictModel;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Districts implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnDelete;

    @FXML
    private ComboBox<CityModel> cbxCities;

    @FXML
    private ComboBox<CountryModel> cbxCountries;

    @FXML
    private TableView<DistrictTable> tableDistricts;

    @FXML
    private TableColumn<DistrictTable, Integer> idDistrict;

    @FXML
    private TableColumn<DistrictTable, String> city;

    @FXML
    private TableColumn<DistrictTable, String> country;

    @FXML
    private TableColumn<DistrictTable, String> district;

    @FXML
    private Label lblTitleForm;

    @FXML
    private TextField txtDistrict;

    @FXML
    private TextField txtFindByDistrict;

    private final ICityDao cityDao = new CityDaoImpl();
    private final ICountryDao countryDao = new CountryDaoImpl();
    private final IAddressDao addressDao = new AddressDaoImpl();
    private final IDistrictDao districtDao = new DistrictDaoImpl();

    @Setter
    private ComboBox<CityModel> cbxCity;
    @Setter
    private ComboBox<CountryModel> cbxCountry;
    @Setter
    private TableView<AddressTable> tableAddress;

    private ArrayList<CountryModel> listCountries;
    private ArrayList<CityModel> listCities;

    private DistrictTable districtModel;
    private static String actionCrud = "add";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.initTable();
        this.loadCountries();
        this.loadCities(0);
    }

    private void initTable() {
        this.idDistrict.setCellValueFactory(new PropertyValueFactory<>("idDistrict"));
        this.district.setCellValueFactory(new PropertyValueFactory<>("districtName"));
        this.city.setCellValueFactory(new PropertyValueFactory<>("cityName"));
        this.country.setCellValueFactory(new PropertyValueFactory<>("countryName"));

        this.loadTable();
    }

    private void loadTable() {
        this.tableDistricts.setItems(this.loadDistricts());
    }

    private void loadCountries() {
        this.listCountries = this.countryDao.load();
        this.cbxCountries.getItems().clear();
        this.cbxCountries.getItems().addAll(this.listCountries);
        this.cbxCountries.setConverter(new CountryConverter());
    }

    private void loadCities(int id) {
        if (id != 0) {
            this.listCities = this.cityDao.listOfCities(id);
        } else {
            this.listCities = this.cityDao.load();
        }
        this.cbxCities.getItems().clear();
        this.cbxCities.getItems().addAll(this.listCities);
        this.cbxCities.setConverter(new CityConverter());
    }

    @FXML
    private void addOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnAdd)) {
            if (!AppUtils.validateText(this.txtDistrict) && !this.validateCombo(this.cbxCities, this.cbxCountries)) {
                if (Districts.actionCrud.equalsIgnoreCase("add")) {
                    this.add();
                } else if (Districts.actionCrud.equalsIgnoreCase("edit")) {
                    if (this.validateSelectedDistricts()) {
                        this.loadModalConfirmation("¿Estas seguro(a) que quieres editar el Distrito");
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
            Districts.actionCrud = "add";
            this.reloadTableAndForm();
            this.changedCrudAction();
        }
    }

    @FXML
    private void comboChangeOnAction(ActionEvent event) {
        if (event.getSource().equals(this.cbxCountries)) {
            if (!this.cbxCountries.getSelectionModel().isEmpty()) {
                var countryId = this.cbxCountries.getSelectionModel().getSelectedItem().getIdCountry();
                this.loadCities(countryId);
            }
        }
    }

    @FXML
    private void deleteOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnDelete)) {
            Districts.actionCrud = "delete";

            if (this.validateSelectedDistricts()) {
                this.loadModalConfirmation("¿Estas seguro(a) que quieres eliminar el Distrito");
            } else {
                AppUtils.loadModalMessage("Selecciona la fila a eliminar", "error");
            }
        }
    }

    @FXML
    private void findByOnKeyTyped(KeyEvent event) {
        if (event.getSource().equals(this.txtFindByDistrict)) {
            var countryName = this.txtFindByDistrict.getText().trim().toUpperCase();

            if (countryName.isEmpty()) {
                this.loadTable();
            } else {
                this.tableDistricts.setItems(this.findByNameDistricts(countryName));
            }
        }
    }

    @FXML
    private void tblRolesUserOnClick(MouseEvent event) {
        if (event.getSource().equals(this.tableDistricts)) {
            if (this.validateSelectedDistricts()) {
                Districts.actionCrud = "edit";
                this.districtModel = this.tableDistricts.getSelectionModel().getSelectedItem();
                this.changedCrudAction();
                this.txtDistrict.setText(this.districtModel.getDistrictName());

                var city = new CityModel(this.districtModel.getCityName());
                var positionCity = this.getPositionCity(this.listCities, city);
                this.cbxCities.getSelectionModel().select(positionCity);

                var country = new CountryModel(this.districtModel.getCountryName());
                var positionCountry = this.getPositionCountry(this.listCountries, country);
                this.cbxCountries.getSelectionModel().select(positionCountry);
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

    private void loadComboCities() {
        this.cbxCity.getItems().clear();
        this.cbxCity.getItems().addAll(this.cityDao.load());
        this.cbxCity.setConverter(new CityConverter());
    }

    private void loadTableAddress() {
        if (this.tableAddress != null)
            this.tableAddress.setItems(this.addressDao.loadTable());
    }

    private void changedCrudAction() {
        var title = (Districts.actionCrud.equalsIgnoreCase("add") ? "Agregar" : "Actualizar");
        this.lblTitleForm.setText(title);
        this.btnAdd.setText(title);
    }

    private boolean validateSelectedDistricts() {
        return this.tableDistricts.getSelectionModel().getSelectedItem() != null;
    }

    private void reloadTableAndForm() {
        AppUtils.clearText(this.txtDistrict);
        this.loadTable();
        this.loadTableAddress();
        this.loadComboCountries();
        this.loadComboCities();
    }

    private boolean validateCombo(ComboBox<CityModel> cbxCity,
                                  ComboBox<CountryModel> cbxCountry) {
        return cbxCity.getSelectionModel().isEmpty()
                || cbxCountry.getSelectionModel().isEmpty();
    }

    private int getPositionCity(ArrayList<CityModel> listCities, CityModel cityModel) {
        for (int i = 0; i < listCities.size(); i++) {
            if (listCities.get(i).getCityName().equalsIgnoreCase(cityModel.getCityName())) {
                return i;
            }
        }

        return 0;
    }

    private int getPositionCountry(ArrayList<CountryModel> listCountries, CountryModel countryModel) {
        for (int i = 0; i < listCountries.size(); i++) {
            if (listCountries.get(i).getCountryName().equalsIgnoreCase(countryModel.getCountryName())) {
                return i;
            }
        }

        return 0;
    }

    private void loadModalConfirmation(String message) {
        var stage = new Stage();

        final ModalConfirmation modalConfirmation = AppUtils.loadModalConfirmation(stage, message);

        modalConfirmation.getLblMessage().setText(message.toUpperCase());
        Images.addImg(AppUtils.urlAlert, modalConfirmation.getIconType());
        modalConfirmation.getBtnOk().setOnAction(actionEvent -> {
            if (Districts.actionCrud.equalsIgnoreCase("edit")) {
                this.edit(actionEvent);
            } else {
                this.delete(actionEvent);
            }
        });
        modalConfirmation.getBtnCancel().setOnAction(actionEvent -> {
            Districts.actionCrud = "add";
            this.changedCrudAction();
            this.reloadTableAndForm();
            AppUtils.closeModal(actionEvent);
        });

        stage.show();
    }

    private void add() {
        var districtName = this.txtDistrict.getText().trim();
        var cityId = this.cbxCities.getSelectionModel().getSelectedItem().getIdCity();
        var districtModel = new DistrictModel(districtName, new CityModel(cityId));

        if (this.districtDao.add(districtModel)) {
            this.reloadTableAndForm();
            AppUtils.loadModalMessage("Distrito Agregado", "success");
        } else {
            AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde", "error");
        }
    }

    private void edit(ActionEvent actionEvent) {
        var districtId = this.tableDistricts.getSelectionModel().getSelectedItem().getIdDistrict();
        var districtName = this.txtDistrict.getText().trim();
        var cityId = this.cbxCities.getSelectionModel().getSelectedItem().getIdCity();
        var districtModel = new DistrictModel(districtId, districtName, new CityModel(cityId));

        if (this.districtDao.update(districtModel)) {
            AppUtils.loadModalMessage("Distrito actualizado", "success");
            AppUtils.closeModal(actionEvent);
        } else {
            AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde", "error");
        }

        Districts.actionCrud = "add";
        this.changedCrudAction();
        this.reloadTableAndForm();
    }

    private void delete(ActionEvent actionEvent) {
        var districtModel = new DistrictModel(this.districtModel.getIdDistrict());
        if (this.districtDao.delete(districtModel)) {
            AppUtils.loadModalMessage("Distrito " + districtModel.getDistrictName()
                    + " eliminado con exito", "success");
            AppUtils.closeModal(actionEvent);
        } else {
            AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde", "error");
        }

        Districts.actionCrud = "add";
        this.changedCrudAction();
        this.reloadTableAndForm();
    }

    private ObservableList<DistrictTable> loadDistricts() {
        final ObservableList<DistrictTable> observableArrayList = FXCollections.observableArrayList();
        observableArrayList.clear();

        var list = this.districtDao.load();
        for (DistrictModel model : list) {
            observableArrayList.add(
                    new DistrictTable(
                            model.getIdDistrict(),
                            model.getDistrictName(),
                            model.getCityModel().getCityName(),
                            model.getCityModel().getCountryModel().getCountryName()
                    )
            );
        }

        return observableArrayList;
    }

    private ObservableList<DistrictTable> findByNameDistricts(String name) {
        final ObservableList<DistrictTable> observableArrayList = FXCollections.observableArrayList();
        observableArrayList.clear();

        var list = this.districtDao.findValue(new DistrictModel(name));
        for (DistrictModel model : list) {
            observableArrayList.add(
                    new DistrictTable(
                            model.getIdDistrict(),
                            model.getDistrictName(),
                            model.getCityModel().getCityName(),
                            model.getCityModel().getCountryModel().getCountryName()
                    )
            );
        }

        return observableArrayList;
    }
}
