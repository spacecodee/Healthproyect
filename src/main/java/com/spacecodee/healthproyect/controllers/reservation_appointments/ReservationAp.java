package com.spacecodee.healthproyect.controllers.reservation_appointments;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

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
    private TableColumn<?, ?> dateReservation;

    @FXML
    private TableColumn<?, ?> dniCustomer;

    @FXML
    private TableColumn<?, ?> dniUser;

    @FXML
    private TableColumn<?, ?> idUser;

    @FXML
    private TableColumn<?, ?> lastNameCustomer;

    @FXML
    private TableColumn<?, ?> nameCustomer;

    @FXML
    private TableColumn<?, ?> price;

    @FXML
    private TableColumn<?, ?> typeReservation;

    @FXML
    private TableColumn<?, ?> userName;

    @FXML
    private TableView<?> tableReservations;

    @FXML
    private TextField txtFindByDni;

    @FXML
    void actionOnAction(ActionEvent event) {

    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void findByOnTyped(KeyEvent event) {

    }

    @FXML
    void reservationTypeOnAction(ActionEvent event) {

    }

    @FXML
    void tblUsersOnClick(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
