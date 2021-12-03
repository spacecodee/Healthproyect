package com.spacecodee.healthproyect.controllers.reservation_appointments;

import com.spacecodee.healthproyect.controllers.modals.ModalConfirmation;
import com.spacecodee.healthproyect.dao.address.AddressDaoImpl;
import com.spacecodee.healthproyect.dao.address.IAddressDao;
import com.spacecodee.healthproyect.dao.customers.CustomerDaoImpl;
import com.spacecodee.healthproyect.dao.customers.ICustomerDao;
import com.spacecodee.healthproyect.dao.peoples.IPeopleDao;
import com.spacecodee.healthproyect.dao.peoples.PeopleDaoImpl;
import com.spacecodee.healthproyect.dao.reservation_appointments.IReservationApDao;
import com.spacecodee.healthproyect.dao.reservation_appointments.ReservationApDaoImpl;
import com.spacecodee.healthproyect.converters.reservation_appointments.ReservationTable;
import com.spacecodee.healthproyect.dao.reserved_days.IReservedDaysDao;
import com.spacecodee.healthproyect.dao.reserved_days.ReservedDaysDaoImpl;
import com.spacecodee.healthproyect.dto.customers.CustomerDto;
import com.spacecodee.healthproyect.dto.peoples.PeopleDto;
import com.spacecodee.healthproyect.dto.reservation_appointments.ReservationApDto;
import com.spacecodee.healthproyect.dto.reserved_days.ReservedDaysDto;
import com.spacecodee.healthproyect.dto.users.UserDto;
import com.spacecodee.healthproyect.utils.AppUtils;
import com.spacecodee.healthproyect.utils.Images;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReservationAp implements Initializable {

    @FXML
    private Button btnAction;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReservationType;

    @FXML
    private TableColumn<ReservationTable, Integer> idReservationAp;

    @FXML
    private TableColumn<ReservationTable, Double> price;

    @FXML
    private TableColumn<ReservationTable, String> dateReservation;

    @FXML
    private TableColumn<ReservationTable, String> dniCustomer;

    @FXML
    private TableColumn<ReservationTable, String> dniUser;

    @FXML
    private TableColumn<ReservationTable, String> lastNameCustomer;

    @FXML
    private TableColumn<ReservationTable, String> nameCustomer;

    @FXML
    private TableColumn<ReservationTable, String> typeReservation;

    @FXML
    private TableColumn<ReservationTable, String> userName;

    @FXML
    private TableView<ReservationTable> tableReservations;

    @FXML
    private TextField txtFindByDni;

    private final IReservationApDao reservationApDao = new ReservationApDaoImpl();
    private final IPeopleDao peopleDao = new PeopleDaoImpl();
    private final ICustomerDao customerDao = new CustomerDaoImpl();
    private final IAddressDao addressDao = new AddressDaoImpl();
    private final IReservedDaysDao reservedDaysDao = new ReservedDaysDaoImpl();
    @Getter
    @Setter
    private ReservationTable reservationTable;
    @Getter
    @Setter
    private UserDto userDto;

    private static String actionCrud = "add";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.initTable();
    }

    private void initTable() {
        this.idReservationAp.setCellValueFactory(new PropertyValueFactory<>("idReservationAppointment"));
        this.nameCustomer.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        this.lastNameCustomer.setCellValueFactory(new PropertyValueFactory<>("customerLastName"));
        this.dniCustomer.setCellValueFactory(new PropertyValueFactory<>("customerDni"));
        this.typeReservation.setCellValueFactory(new PropertyValueFactory<>("typeReservation"));
        this.price.setCellValueFactory(new PropertyValueFactory<>("priceReservation"));
        this.dateReservation.setCellValueFactory(new PropertyValueFactory<>("dateReservation"));
        this.userName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        this.dniUser.setCellValueFactory(new PropertyValueFactory<>("userDni"));

        this.loadTable();
    }

    private void loadTable() {
        this.tableReservations.setItems(this.loadReservations());
    }

    @FXML
    private void actionOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnAction)) {
            Stage stage = new Stage();
            var reservationAp = AppUtils.loadReservationsApModal(stage, "Reservaciones");
            if (ReservationAp.actionCrud.equalsIgnoreCase("add")) {
                reservationAp.getBtnSave().setOnAction(actionEvent -> {
                    if (reservationAp.getCustomerDto() != null) {
                        if (!reservationAp.validateTextEdit()) {
                            var reservationApModel = reservationAp.returnReservationAp();
                            this.add(reservationApModel);
                            AppUtils.closeModal(actionEvent);
                        } else {
                            AppUtils.loadModalMessage("Todos los datos son necesarios", "error");
                        }
                    } else {
                        if (!reservationAp.validateText()) {
                            var reservationApModel = reservationAp.returnReservationAp();
                            this.add(reservationApModel);
                            AppUtils.closeModal(actionEvent);
                        } else {
                            AppUtils.loadModalMessage("Todos los datos son necesarios", "error");
                        }
                    }
                });
                stage.show();
            } else if (ReservationAp.actionCrud.equalsIgnoreCase("edit")) {
                if (this.validateSelectedReservationApTable()) {
                    reservationAp.setReservationTable(this.reservationTable);
                    reservationAp.sendData();
                    reservationAp.disableSections(true);

                    reservationAp.getBtnSave().setOnAction(actionEvent -> {
                        if (!reservationAp.validateTextEdit()) {
                            var reservationApModel = reservationAp.returnReservationAp();
                            this.loadModalConfirmation("¿Estas seguro(a) que quieres editar esta reservación?",
                                    actionEvent, reservationApModel);
                        } else {
                            AppUtils.loadModalMessage("Todos los datos son necesarios", "error");
                        }
                    });
                    stage.show();
                }
            }
        }
    }

    @FXML
    private void btnCancelOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnCancel)) {
            this.loadTable();
            this.changedCrudAction("Add");
        }
    }

    @FXML
    private void btnDeleteOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnDelete)) {
            this.changedCrudAction("Add");

            if (this.validateSelectedReservationApTable()) {
                this.loadModalConfirmation("¿Estas seguro(a) que quieres eliminar esta reservación",
                        null, null);
            } else {
                AppUtils.loadModalMessage("Selecciona la fila a eliminar", "error");
            }
        }
    }

    @FXML
    private void findByOnTyped(KeyEvent event) {
        if (event.getSource().equals(this.txtFindByDni)) {
            var dni = this.txtFindByDni.getText().trim();

            if (dni.isEmpty()) {
                this.loadTable();
            } else {
                this.tableReservations.setItems(this.findReservations(dni));
            }
        }
    }

    @FXML
    private void reservationTypeOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnReservationType)) {
            Stage stage = new Stage();
            AppUtils.loadTypeReservation(stage, "Tipo de reservas");
            stage.show();
        }
    }

    @FXML
    private void tblUsersOnClick(MouseEvent event) {
        if (event.getSource().equals(this.tableReservations)) {
            if (this.validateSelectedReservationApTable()) {
                this.changedCrudAction("edit");
                this.setReservationTable(this.tableReservations.getSelectionModel().getSelectedItem());
            }
        }
    }

    private void loadModalConfirmation(String message, ActionEvent actionEvent2, ReservationApDto reservationApDto) {
        var stage = new Stage();
        final ModalConfirmation modalConfirmation = AppUtils.loadModalConfirmation(stage, message);

        modalConfirmation.getLblMessage().setText(message.toUpperCase());
        Images.addImg(AppUtils.urlAlert, modalConfirmation.getIconType());
        modalConfirmation.getBtnOk().setOnAction(actionEvent -> {
            if (ReservationAp.actionCrud.equalsIgnoreCase("edit")) {
                this.edit(reservationApDto, actionEvent, actionEvent2);
            } else {
                this.delete(actionEvent);
            }
        });
        modalConfirmation.getBtnCancel().setOnAction(actionEvent -> {
            this.changedCrudAction("Add");
            this.loadTable();
            AppUtils.closeModal(actionEvent);
        });

        stage.show();
    }

    private boolean validateSelectedReservationApTable() {
        return this.tableReservations.getSelectionModel().getSelectedItem() != null;
    }

    private void changedCrudAction(String type) {
        ReservationAp.actionCrud = type;
        this.btnAction.setText(type.equalsIgnoreCase("Edit") ? "Editar" : "Agregar");
    }

    private void add(ReservationApDto reservationApDto) {
        if (reservationApDto.getCustomer().getIdCustomer() != 0) {
            this.addReservation(reservationApDto);
        } else {
            if (this.addressDao.add(reservationApDto.getCustomer().getPeople().getAddressDto())) {
                var idAddress = this.addressDao.returnMaxId();
                if (idAddress != 0) {
                    reservationApDto.getCustomer().getPeople().getAddressDto().setIdAddress(idAddress);

                    if (this.peopleDao.add(reservationApDto.getCustomer().getPeople())) {
                        var idPeople = this.peopleDao.returnMaxId();
                        if (idPeople != 0) {
                            reservationApDto.getCustomer().getPeople().setIdPeople(idPeople);
                            if (this.customerDao.add(reservationApDto.getCustomer())) {
                                var idCustomer = this.customerDao.returnMaxId();
                                if (idCustomer != 0) {
                                    reservationApDto.getCustomer().setIdCustomer(idCustomer);

                                    this.addReservation(reservationApDto);
                                }
                            }
                        }
                    }
                }
            } else {
                AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde", "error");
            }
        }

    }

    private void addReservation(ReservationApDto reservationApDto) {
        if (this.reservedDaysDao.add(reservationApDto.getReservedDays())) {
            var idReservedDays = this.reservedDaysDao.returnMaxId();

            if (idReservedDays != 0) {
                reservationApDto.getReservedDays().setIdReservedDay(idReservedDays);

                if (this.reservationApDao.add(reservationApDto)) {
                    AppUtils.loadModalMessage("Reserva Agregada", "success");
                } else {
                    AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde",
                            "error");
                }
                this.loadTable();
            }
        }
    }

    private void delete(ActionEvent actionEvent) {
        var idReservationAp = this.tableReservations.getSelectionModel().getSelectedItem().getIdReservationAppointment();
        var idReservedDays = this.tableReservations.getSelectionModel().getSelectedItem().getIdReservationDate();
        var reservationApDto = new ReservationApDto(idReservationAp);
        var reservedDays = new ReservedDaysDto(idReservedDays);
        if (this.reservationApDao.delete(reservationApDto) && this.reservedDaysDao.delete(reservedDays)) {
            AppUtils.loadModalMessage("Reservación eliminada con exito", "success");
            AppUtils.closeModal(actionEvent);
        } else {
            AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde", "error");
        }

        this.loadTable();
        this.changedCrudAction("add");
    }

    private void edit(ReservationApDto reservationApDto, ActionEvent... actionEvent) {
        if (this.reservedDaysDao.update(reservationApDto.getReservedDays())) {
            var idReservedDays = reservationApDto.getReservedDays().getIdReservedDay();
            if (idReservedDays != 0) {
                if (this.reservationApDao.update(reservationApDto)) {
                    AppUtils.loadModalMessage("Reserva Actualizada", "success");
                } else {
                    AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde",
                            "error");
                }

                this.loadTable();
                this.changedCrudAction("add");
            } else {
                AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde", "error");
            }
        } else {
            AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde", "error");
        }

        for (ActionEvent event : actionEvent) {
            AppUtils.closeModal(event);
        }
    }

    private ObservableList<ReservationTable> loadReservations() {
        final ObservableList<ReservationTable> observableArrayList = FXCollections.observableArrayList();
        observableArrayList.clear();

        var list = this.reservationApDao.load();
        return getReservationTables(observableArrayList, list);
    }

    private ObservableList<ReservationTable> findReservations(String dni) {
        final ObservableList<ReservationTable> observableArrayList = FXCollections.observableArrayList();
        observableArrayList.clear();

        var reservation = new ReservationApDto(new CustomerDto(new PeopleDto(dni)));

        var list = this.reservationApDao.findValue(reservation);
        return getReservationTables(observableArrayList, list);
    }

    private ObservableList<ReservationTable> getReservationTables(ObservableList<ReservationTable> observableArrayList,
                                                                  ArrayList<ReservationApDto> list) {
        for (ReservationApDto model : list) {
            observableArrayList.add(
                    new ReservationTable(
                            model.getIdReservationQuote(),
                            model.getCustomer().getPeople().getIdPeople(),
                            model.getCustomer().getIdCustomer(),
                            model.getUser().getIdUser(),
                            model.getTypeReservation().getIdTypeReservation(),
                            model.getReservedDays().getIdReservedDay(),
                            model.getCustomer().getPeople().getName(),
                            model.getCustomer().getPeople().getLastname(),
                            model.getCustomer().getPeople().getDni(),
                            model.getTypeReservation().getNameReservation(),
                            model.getTypeReservation().getPriceReservation(),
                            model.getReservedDays().getReservationDate(),
                            model.getUser().getUserName(),
                            model.getUser().getPeople().getDni()
                    )
            );
        }

        return observableArrayList;
    }
}
