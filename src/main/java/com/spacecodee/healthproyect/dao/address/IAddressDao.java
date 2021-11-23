package com.spacecodee.healthproyect.dao.address;

import com.spacecodee.healthproyect.dao.ICrudGeneric;
import com.spacecodee.healthproyect.dto.address.AddressTable;
import com.spacecodee.healthproyect.model.address.AddressModel;
import javafx.collections.ObservableList;

public interface IAddressDao extends ICrudGeneric<AddressModel> {

    ObservableList<AddressTable> loadTable();

    ObservableList<AddressTable> findByNameTable(AddressTable addressModel);

    int returnMaxId();
}
