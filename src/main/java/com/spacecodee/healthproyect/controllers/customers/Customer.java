package com.spacecodee.healthproyect.controllers.customers;

import com.spacecodee.healthproyect.dao.ICrudGeneric;
import com.spacecodee.healthproyect.dao.customers.CustomerDaoImpl;
import com.spacecodee.healthproyect.model.customers.CustomerModel;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Customer implements Initializable {

    private final ICrudGeneric<CustomerModel> customerDao = new CustomerDaoImpl();
    private CustomerModel CustomerModel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
