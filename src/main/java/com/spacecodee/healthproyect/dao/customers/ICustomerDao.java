package com.spacecodee.healthproyect.dao.customers;

import com.spacecodee.healthproyect.dao.ICrudGeneric;
import com.spacecodee.healthproyect.dto.customers.CustomerDto;

public interface ICustomerDao extends ICrudGeneric<CustomerDto> {

    CustomerDto findCustomerByDni(String dni);

    int returnMaxId();
}
