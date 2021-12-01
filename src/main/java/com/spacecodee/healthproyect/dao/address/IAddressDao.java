package com.spacecodee.healthproyect.dao.address;

import com.spacecodee.healthproyect.dao.ICrudGeneric;
import com.spacecodee.healthproyect.dto.address.AddressDto;

public interface IAddressDao extends ICrudGeneric<AddressDto> {
    int returnMaxId();
}
