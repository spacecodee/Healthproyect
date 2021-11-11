package com.spacecodee.healthproyect.dto.city;

import com.spacecodee.healthproyect.model.cities.CityModel;
import javafx.util.StringConverter;

public class CityConverter extends StringConverter<CityModel> {

    @Override
    public String toString(CityModel cityModel) {
        return cityModel != null ? cityModel.getCityName() : null;
    }

    @Override
    public CityModel fromString(String s) {
        return null;
    }
}
