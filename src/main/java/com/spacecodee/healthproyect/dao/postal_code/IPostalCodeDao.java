package com.spacecodee.healthproyect.dao.postal_code;

import com.spacecodee.healthproyect.dao.ICrudGeneric;
import com.spacecodee.healthproyect.model.postal_codes.PostalCodeModel;

public interface IPostalCodeDao extends ICrudGeneric<PostalCodeModel> {

    int getMaxId();
}
