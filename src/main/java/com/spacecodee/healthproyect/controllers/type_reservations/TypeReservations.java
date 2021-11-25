package com.spacecodee.healthproyect.controllers.type_reservations;

import com.spacecodee.healthproyect.controllers.modals.ModalConfirmation;
import com.spacecodee.healthproyect.dao.type_reservations.ITypeReservationsDao;
import com.spacecodee.healthproyect.dao.type_reservations.TypeReservationsDaoImpl;
import com.spacecodee.healthproyect.model.type_reservations.TypeReservationModel;
import com.spacecodee.healthproyect.utils.AppUtils;
import com.spacecodee.healthproyect.utils.Images;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

public class TypeReservations implements Initializable {

    @FXML
    private Button btnAction;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnDelete;

    @FXML
    private Label lblTitle;

    @FXML
    private TableView<TypeReservationModel> tableTypeReservation;

    @FXML
    private TableColumn<TypeReservationModel, Integer> idTypeReservation;

    @FXML
    private TableColumn<TypeReservationModel, String> typeReservation;

    @FXML
    private TableColumn<TypeReservationModel, Double> price;

    @FXML
    private TextField txtNameReservation;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtSearch;

    private final ITypeReservationsDao typeReservationsDao = new TypeReservationsDaoImpl();

    @Getter
    @Setter
    private TypeReservationModel typeReservationModel;

    private static String actionCrud = "add";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.txtSearch.addEventFilter(KeyEvent.KEY_TYPED, AppUtils.letterValidation(500));
        this.txtPrice.addEventFilter(KeyEvent.KEY_TYPED, AppUtils.numericValidation(500));
        this.initTable();
    }

    private void initTable() {
        this.idTypeReservation.setCellValueFactory(new PropertyValueFactory<>("idTypeReservation"));
        this.typeReservation.setCellValueFactory(new PropertyValueFactory<>("nameReservation"));
        this.price.setCellValueFactory(new PropertyValueFactory<>("priceReservation"));

        this.loadTable();
    }

    private void loadTable() {
        this.tableTypeReservation.setItems(this.typeReservationsDao.load());
    }

    @FXML
    private void actionOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnAction)) {
            if (TypeReservations.actionCrud.equalsIgnoreCase("add")) {
                if (!AppUtils.validateText(this.txtNameReservation, this.txtPrice)) {
                    var nameReservation = this.txtNameReservation.getText().trim();
                    var priceReservation = Double.parseDouble(this.txtPrice.getText().trim());

                    var typeReservationModel = new TypeReservationModel(nameReservation, priceReservation);
                    if (this.typeReservationsDao.add(typeReservationModel)) {
                        this.reloadTableAndForm();
                        AppUtils.loadModalMessage("Tipo de reserva agregada", "success");
                    } else {
                        AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde", "error");
                    }
                } else {
                    AppUtils.loadModalMessage("Todos los datos son necesarios", "error");
                }
            } else if (TypeReservations.actionCrud.equalsIgnoreCase("edit")) {
                if (this.validateSelectedUserTable() || !AppUtils.validateText(this.txtNameReservation, this.txtPrice)) {
                    this.loadModalConfirmation("¿Estas seguro(a) que quieres editar el tipo de reserva?");
                } else {
                    AppUtils.loadModalMessage("Selecciona la fila a editar", "error");
                }
            }
        }
    }

    @FXML
    private void cancelOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnCancel)) {
            this.reloadTableAndForm();
            this.changedCrudAction("Add");
        }
    }

    @FXML
    private void deleteOnAction(ActionEvent event) {
        if (event.getSource().equals(this.btnDelete)) {
            TypeReservations.actionCrud = "delete";

            if (this.validateSelectedUserTable()) {
                this.loadModalConfirmation("¿Estas seguro(a) que quieres eliminar este tipo de reservación");
            } else {
                AppUtils.loadModalMessage("Selecciona la fila a eliminar", "error");
            }
        }
    }

    @FXML
    private void findByNameOnKeyTyped(KeyEvent event) {
        if (event.getSource().equals(this.txtSearch)) {
            var name = this.txtSearch.getText().trim();

            if (name.isEmpty()) {
                this.loadTable();
            } else {
                this.tableTypeReservation.setItems(this.typeReservationsDao.findByName(name));
            }

        }
    }

    @FXML
    private void tblTypeReservationOnClick(MouseEvent event) {
        if (event.getSource().equals(this.tableTypeReservation)) {
            if (this.validateSelectedUserTable()) {
                this.setTypeReservationModel(this.tableTypeReservation.getSelectionModel().getSelectedItem());
                this.txtNameReservation.setText(this.typeReservationModel.getNameReservation());
                this.txtPrice.setText(String.valueOf(this.typeReservationModel.getPriceReservation()));
                this.changedCrudAction("edit");
            }
        }
    }

    private void loadModalConfirmation(String message) {
        var stage = new Stage();
        final ModalConfirmation modalConfirmation = AppUtils.loadModalConfirmation(stage, message);

        modalConfirmation.getLblMessage().setText(message.toUpperCase());
        Images.addImg(AppUtils.urlAlert, modalConfirmation.getIconType());
        modalConfirmation.getBtnOk().setOnAction(actionEvent -> {
            if (TypeReservations.actionCrud.equalsIgnoreCase("edit")) {
                this.edit(actionEvent);
            } else {
                this.delete(actionEvent);
            }
        });
        modalConfirmation.getBtnCancel().setOnAction(actionEvent -> {
            this.changedCrudAction("add");
            this.reloadTableAndForm();
            AppUtils.closeModal(actionEvent);
        });

        stage.show();
    }

    private void edit(ActionEvent actionEvent) {
        var typeReservationName = this.txtNameReservation.getText().trim();
        var typeReservationPrice = Double.parseDouble(this.txtPrice.getText().trim());
        this.typeReservationModel.setNameReservation(typeReservationName);
        this.typeReservationModel.setPriceReservation(typeReservationPrice);

        if (this.typeReservationsDao.update(this.typeReservationModel)) {
            AppUtils.loadModalMessage("Tipo de reserva Actualizado", "success");
        } else {
            AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde", "error");
        }

        AppUtils.closeModal(actionEvent);

        this.changedCrudAction("add");
        this.reloadTableAndForm();
    }

    private void delete(ActionEvent actionEvent) {
        var typeReservation = new TypeReservationModel(this.typeReservationModel.getIdTypeReservation());

        if (this.typeReservationsDao.delete(typeReservation)) {
            AppUtils.loadModalMessage("Tipo de reservación eliminada con exito", "success");
            AppUtils.closeModal(actionEvent);
        } else {
            AppUtils.loadModalMessage("Al parecer ocurrio un error, intentalo mas tarde", "error");
        }
        this.changedCrudAction("add");
        this.reloadTableAndForm();
    }

    private boolean validateSelectedUserTable() {
        return this.tableTypeReservation.getSelectionModel().getSelectedItem() != null;
    }

    private void changedCrudAction(String type) {
        TypeReservations.actionCrud = type;
        this.lblTitle.setText((type.equalsIgnoreCase("add")) ? "Agregar Reservaciones" : "Actualizar Reservaciones");
        this.btnAction.setText((type.equalsIgnoreCase("add")) ? "Agregar" : "Actualizar");
    }

    private void reloadTableAndForm() {
        AppUtils.clearText(this.txtPrice, this.txtNameReservation);
        this.loadTable();
    }
}
