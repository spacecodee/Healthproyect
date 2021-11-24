package com.spacecodee.healthproyect.dao.customers;

import com.spacecodee.healthproyect.dao.ICrudGeneric;
import com.spacecodee.healthproyect.dto.customer.CustomerTable;
import com.spacecodee.healthproyect.dto.user.UserTable;
import com.spacecodee.healthproyect.model.customers.CustomerModel;
import javafx.collections.ObservableList;

public interface ICustomerDao extends ICrudGeneric<CustomerModel> {

    ObservableList<CustomerTable> loadTable();

    ObservableList<CustomerTable> findByNameAndDniTable(CustomerTable customerTable);
}
