package com.spacecodee.healthproyect.dao.countries;

import com.spacecodee.healthproyect.dao.ICrudGeneric;
import com.spacecodee.healthproyect.dto.countries.CountryDto;

public interface ICountryDao extends ICrudGeneric<CountryDto> {

    int getMaxId();
}
