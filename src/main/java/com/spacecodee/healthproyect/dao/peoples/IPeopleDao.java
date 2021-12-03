package com.spacecodee.healthproyect.dao.peoples;

import com.spacecodee.healthproyect.dao.ICrudGeneric;
import com.spacecodee.healthproyect.dto.peoples.PeopleDto;

public interface IPeopleDao extends ICrudGeneric<PeopleDto> {


    PeopleDto findPeopleByDni(String dni);

    int returnMaxId();
}
