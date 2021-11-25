package com.spacecodee.healthproyect.dao.cities;

import com.spacecodee.healthproyect.dao.ICrudGeneric;
import com.spacecodee.healthproyect.model.cities.CityModel;

import java.util.ArrayList;

public interface ICityDao extends ICrudGeneric<CityModel> {

    ArrayList<CityModel> listOfCities(int id);

    int getMaxId();
}
