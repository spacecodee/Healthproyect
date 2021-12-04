package com.spacecodee.healthproyect.controllers.menu;

import com.spacecodee.healthproyect.dao.customers.CustomerDaoImpl;
import com.spacecodee.healthproyect.dao.customers.ICustomerDao;
import com.spacecodee.healthproyect.dao.type_reservations.ITypeReservationsDao;
import com.spacecodee.healthproyect.dao.type_reservations.TypeReservationsDaoImpl;
import com.spacecodee.healthproyect.dao.users.IUserDao;
import com.spacecodee.healthproyect.dao.users.UserDaoImpl;
import com.spacecodee.healthproyect.dto.users.UserDto;
import com.spacecodee.healthproyect.utils.Images;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

public class Menu implements Initializable {

    @FXML
    private ImageView iconCustomer;

    @FXML
    private ImageView iconTypeReservations;

    @FXML
    private ImageView iconUsers;

    @FXML
    private Label lblCountCustomers;

    @FXML
    private Label lblCountUsers;

    @Getter
    @FXML
    private Label lblTitle;

    @FXML
    private Label lblTypeReservations;

    @Getter
    @Setter
    private UserDto userDto;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Images.addImg("icons/user.png", this.iconUsers);
        Images.addImg("icons/customers.png", this.iconCustomer);
        Images.addImg("icons/type-reserved.png", this.iconTypeReservations);
        this.totalUsers();
        this.totalCustomers();
        this.totalTypeReservations();
    }

    private void totalUsers() {
        final IUserDao userDao = new UserDaoImpl();
        var total = userDao.total();
        this.lblCountUsers.setText(String.valueOf(total));
    }

    private void totalCustomers() {
        final ICustomerDao customerDao = new CustomerDaoImpl();
        var total = customerDao.total();
        this.lblCountCustomers.setText(String.valueOf(total));
    }

    private void totalTypeReservations() {
        final ITypeReservationsDao typeReservationsDao = new TypeReservationsDaoImpl();
        var total = typeReservationsDao.total();
        this.lblTypeReservations.setText(String.valueOf(total));
    }
}
