package com.spacecodee.healthproyect.dao.address;

import com.spacecodee.healthproyect.dao.ICrudGeneric;
import com.spacecodee.healthproyect.model.address.AddressModel;

public interface IAddressDao extends ICrudGeneric<AddressModel> {
    int returnMaxId();
}
