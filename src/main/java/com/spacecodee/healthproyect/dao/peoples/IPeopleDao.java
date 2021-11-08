package com.spacecodee.healthproyect.dao.peoples;

import com.spacecodee.healthproyect.dao.ICrudGeneric;
import com.spacecodee.healthproyect.model.peoples.PeopleModel;

public interface IPeopleDao extends ICrudGeneric<PeopleModel> {

    PeopleModel findPeopleByDni(String dni);
}
