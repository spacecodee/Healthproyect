package com.spacecodee.healthproyect.dao.countries;

import com.spacecodee.healthproyect.dao.ICrudGeneric;
import com.spacecodee.healthproyect.model.countries.CountryModel;
import com.spacecodee.healthproyect.model.countries.CountryTable;
import javafx.collections.ObservableList;

public interface ICountryDao extends ICrudGeneric<CountryModel> {

    ObservableList<CountryTable> loadTable();
    ObservableList<CountryTable> findByNameTable(String name);
}
