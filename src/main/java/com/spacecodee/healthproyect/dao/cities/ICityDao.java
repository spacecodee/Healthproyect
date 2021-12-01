package com.spacecodee.healthproyect.dao.cities;

import com.spacecodee.healthproyect.dao.ICrudGeneric;
import com.spacecodee.healthproyect.dto.cities.CityDto;

import java.util.ArrayList;

public interface ICityDao extends ICrudGeneric<CityDto> {

    ArrayList<CityDto> listOfCities(int id);

    int getMaxId();
}
