package com.spacecodee.healthproyect.controllers.reservation_appointments;

import com.spacecodee.healthproyect.dao.reservation_appointments.IReservationApDao;
import com.spacecodee.healthproyect.dao.reservation_appointments.ReservationApDaoImpl;
import com.spacecodee.healthproyect.dto.reservation_appointments.ReservationTable;
import com.spacecodee.healthproyect.model.reservation_appointments.ReservationApModel;
import com.spacecodee.healthproyect.utils.AppUtils;
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

import java.net.URL;
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

    }

    @FXML
    private void btnCancelOnAction(ActionEvent event) {

    }

    @FXML
    private void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    private void findByOnTyped(KeyEvent event) {

    }

    @FXML
    private void reservationTypeOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnReservationType)) {
            Stage stage = new Stage();
            var typeReservation = AppUtils.loadTypeReservation(stage, "Tipo de reservas");
            stage.show();
        }
    }

    @FXML
    private void tblUsersOnClick(MouseEvent event) {

    }

    private ObservableList<ReservationTable> loadReservations() {
        final ObservableList<ReservationTable> observableArrayList = FXCollections.observableArrayList();
        observableArrayList.clear();

        var list = this.reservationApDao.load();
        for (ReservationApModel model : list) {
            observableArrayList.add(
                    new ReservationTable(
                            model.getIdReservationQuote(),
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
