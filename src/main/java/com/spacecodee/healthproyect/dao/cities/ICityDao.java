package com.spacecodee.healthproyect.dao.cities;

import com.spacecodee.healthproyect.dao.ICrudGeneric;
import com.spacecodee.healthproyect.dto.city.CityTable;
import com.spacecodee.healthproyect.model.cities.CityModel;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public interface ICityDao extends ICrudGeneric<CityModel> {

    ObservableList<CityTable> loadTable();

    ObservableList<CityTable> findByNameTable(String cityName);

    ArrayList<CityModel> listOfCities();

    ArrayList<CityModel> listOfCities(int id);

    int getMaxId();
}
