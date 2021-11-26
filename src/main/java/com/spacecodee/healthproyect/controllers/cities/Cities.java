package com.spacecodee.healthproyect.controllers.cities;

import com.spacecodee.healthproyect.controllers.modals.ModalConfirmation;
import com.spacecodee.healthproyect.dao.address.AddressDaoImpl;
import com.spacecodee.healthproyect.dao.address.IAddressDao;
import com.spacecodee.healthproyect.dao.cities.CityDaoImpl;
import com.spacecodee.healthproyect.dao.cities.ICityDao;
import com.spacecodee.healthproyect.dao.countries.CountryDaoImpl;
import com.spacecodee.healthproyect.dao.countries.ICountryDao;
import com.spacecodee.healthproyect.dto.address.AddressTable;
import com.spacecodee.healthproyect.dto.city.CityConverter;
import com.spacecodee.healthproyect.dto.city.CityTable;
import com.spacecodee.healthproyect.dto.country.CountryConverter;
import com.spacecodee.healthproyect.model.address.AddressModel;
import com.spacecodee.healthproyect.model.cities.CityModel;
import com.spacecodee.healthproyect.model.countries.CountryModel;
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

public class Cities implements Initializable {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnDelete;

    @FXML
    private ComboBox<CountryModel> cbxCountries;

    @FXML
    private TableView<CityTable> tableCities;

    @FXML
    private TableColumn<CityTable, Integer> idCity;

    @FXML
    private TableColumn<CityTable, String> city;

    @FXML
    private TableColumn<CityTable, String> country;

    @FXML
    private Label lblTitleForm;

    @FXML
    private TextField txtCity;

    @FXML
    private TextField txtFindByCity;

    private final ICityDao cityDao = new CityDaoImpl();
    private final ICountryDao countryDao = new CountryDaoImpl();
    private final IAddressDao addressDao = new AddressDaoImpl();

    @Setter
    private ComboBox<CityModel> cbxCity;
    @Setter
    private TableView<AddressTable> tableAddress;

    private CityTable cityModel;
    private ArrayList<CountryModel> listCountries;

    private static String actionCrud = "add";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.initTable();
        this.loadCountries();
    }

    private void loadCountries() {
        this.listCountries = this.countryDao.load();
        this.cbxCountries.getItems().clear();
        this.cbxCountries.getItems().addAll(this.listCountries);
        this.cbxCountries.setConverter(new CountryConverter());
    }

    private void loadComboCities() {
        this.cbxCity.getItems().clear();
        this.cbxCity.getItems().addAll(this.cityDao.load());
        this.cbxCity.setConverter(new CityConverter());
    }

    private void loadTableAddress() {
        if (this.tableAddress != null)
            this.tableAddress.setItems(this.loadAddress());
    }

    private void initTable() {
        this.idCity.setCellValueFactory(new PropertyValueFactory<>("idCity"));
        this.city.setCellValueFactory(new PropertyValueFactory<>("cityName"));
        this.country.setCellValueFactory(new PropertyValueFactory<>("countryName"));

        this.loadTable();
    }

    private void loadTable() {
        this.tableCities.setItems(this.loadDistricts());
    }

    @FXML
    private void addOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnAdd)) {
            if (!AppUtils.validateText(this.txtCity) && !this.validateCombo(this.cbxCountries)) {
                if (Cities.actionCrud.equalsIgnoreCase("add")) {
                    this.add();
                } else if (Cities.actionCrud.equalsIgnoreCase("edit")) {
                    if (this.validateSelectedCities()) {
                        this.loadModalConfirmation("¿Estas seguro(a) que quieres editar la ciudad");
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
            Cities.actionCrud = "add";
            this.reloadTableAndForm();
            this.changedCrudAction();
        }
    }

    @FXML
    private void deleteOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnDelete)) {
            Cities.actionCrud = "delete";

            if (this.validateSelectedCities()) {
                this.loadModalConfirmation("¿Estas seguro(a) que quieres eliminar la Ciudad");
            } else {
                AppUtils.loadModalMessage("Selecciona la fila a eliminar", "error");
            }
        }
    }

    @FXML
    private void findByOnKeyTyped(KeyEvent event) {
        if (event.getSource().equals(this.txtFindByCity)) {
            var countryName = this.txtFindByCity.getText().trim().toUpperCase();

            if (countryName.isEmpty()) {
                this.loadTable();
            } else {
                this.tableCities.setItems(this.findByNameDistricts(countryName));
            }
        }
    }

    @FXML
    private void tblRolesUserOnClick(MouseEvent event) {
        if (event.getSource().equals(this.tableCities)) {
            if (this.validateSelectedCities()) {
                Cities.actionCrud = "edit";
                this.cityModel = this.tableCities.getSelectionModel().getSelectedItem();
                this.changedCrudAction();
                this.txtCity.setText(this.cityModel.getCityName());

                var country = new CountryModel(this.cityModel.getCountryName());
                var positionCountry = this.getPositionCountry(this.listCountries, country);
                this.cbxCountries.getSelectionModel().select(positionCountry);
            }
        }
    }

    private void changedCrudAction() {
        var title = (Cities.actionCrud.equalsIgnoreCase("add") ? "Agregar" : "Actualizar");
        this.lblTitleForm.setText(title);
        this.btnAdd.setText(title);
    }

    private boolean validateSelectedCities() {
        return this.tableCities.getSelectionModel().getSelectedItem() != null;
    }

    private void reloadTableAndForm() {
        AppUtils.clearText(this.txtCity);
        this.loadTable();
        this.loadTableAddress();
        this.loadComboCities();
        this.loadCountries();
    }

    private void loadModalConfirmation(String message) {
        var stage = new Stage();

        final ModalConfirmation modalConfirmation = AppUtils.loadModalConfirmation(stage, message);

        modalConfirmation.getLblMessage().setText(message.toUpperCase());
        Images.addImg(AppUtils.urlAlert, modalConfirmation.getIconType());
        modalConfirmation.getBtnOk().setOnAction(actionEvent -> {
            if (Cities.actionCrud.equalsIgnoreCase("edit")) {
                this.edit(actionEvent);
            } else {
                this.delete(actionEvent);
            }
        });
        modalConfirmation.getBtnCancel().setOnAction(actionEvent -> {
            Cities.actionCrud = "add";
            this.changedCrudAction();
            this.reloadTableAndForm();
            AppUtils.closeModal(actionEvent);
        });

        stage.show();
    }

    private void add() {
        var cityName = this.txtCity.getText().trim();
        var countryId = this.cbxCountries.getSelectionModel().getSelectedItem().getIdCountry();
        var cityModel = new CityModel(cityName, new CountryModel(countryId));

        if (this.cityDao.add(cityModel)) {
            AppUtils.loadModalMessage("Ciudad Agregada", "success");
        } else {
            AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde", "error");
        }
        this.reloadTableAndForm();
    }

    private void edit(ActionEvent actionEvent) {
        var cityId = this.cityModel.getIdCity();
        var cityName = this.txtCity.getText().trim();
        var countryId = this.cbxCountries.getSelectionModel().getSelectedItem().getIdCountry();
        var cityModel = new CityModel(cityId, cityName, new CountryModel(countryId));

        if (this.cityDao.update(cityModel)) {
            AppUtils.loadModalMessage("País actualizado", "success");
            AppUtils.closeModal(actionEvent);
        } else {
            AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde", "error");
        }

        Cities.actionCrud = "add";
        this.changedCrudAction();
        this.reloadTableAndForm();
    }

    private void delete(ActionEvent actionEvent) {
        var cityModel = new CityModel(this.cityModel.getIdCity());
        if (this.cityDao.delete(cityModel)) {
            AppUtils.loadModalMessage("Ciudad " + cityModel.getCityName()
                    + " eliminada con exito", "success");
            AppUtils.closeModal(actionEvent);
        } else {
            AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde", "error");
        }

        Cities.actionCrud = "add";
        this.changedCrudAction();
        this.reloadTableAndForm();
    }

    private boolean validateCombo(ComboBox<CountryModel> cbxCity) {
        return cbxCity.getSelectionModel().isEmpty();
    }

    private int getPositionCountry(ArrayList<CountryModel> listCountries, CountryModel countryModel) {
        for (int i = 0; i < listCountries.size(); i++) {
            if (listCountries.get(i).getCountryName().equalsIgnoreCase(countryModel.getCountryName())) {
                return i;
            }
        }

        return 0;
    }

    private ObservableList<CityTable> loadDistricts() {
        final ObservableList<CityTable> observableArrayList = FXCollections.observableArrayList();
        observableArrayList.clear();

        var list = this.cityDao.load();
        for (CityModel model : list) {
            observableArrayList.add(
                    new CityTable(
                            model.getIdCity(),
                            model.getCityName(),
                            model.getCountryModel().getCountryName()
                    )
            );
        }

        return observableArrayList;
    }

    private ObservableList<CityTable> findByNameDistricts(String name) {
        final ObservableList<CityTable> observableArrayList = FXCollections.observableArrayList();
        observableArrayList.clear();

        var list = this.cityDao.findValue(new CityModel(name));

        for (CityModel model : list) {
            observableArrayList.add(
                    new CityTable(
                            model.getIdCity(),
                            model.getCityName(),
                            model.getCountryModel().getCountryName()
                    )
            );
        }

        return observableArrayList;
    }

    //
    private ObservableList<AddressTable> loadAddress() {
        final ObservableList<AddressTable> observableArrayList = FXCollections.observableArrayList();
        observableArrayList.clear();

        var list = this.addressDao.load();
        for (AddressModel model : list) {
            observableArrayList.add(
                    new AddressTable(
                            model.getIdAddress(),
                            model.getDistrictModel().getCityModel().getCountryModel().getCountryName(),
                            model.getDistrictModel().getCityModel().getCityName(),
                            model.getDistrictModel().getDistrictName(),
                            model.getAddressName()
                    )
            );
        }

        return observableArrayList;
    }
}
