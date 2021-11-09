package com.spacecodee.healthproyect.dao.countries;

import com.spacecodee.healthproyect.dao.ICrudGeneric;
import com.spacecodee.healthproyect.model.countries.CountryModel;

import java.util.ArrayList;

public interface ICountryDao extends ICrudGeneric<CountryModel> {

    ArrayList<CountryModel> listOfCountries();

    int getMaxId();
}
