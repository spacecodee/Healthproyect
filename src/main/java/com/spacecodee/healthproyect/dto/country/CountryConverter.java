package com.spacecodee.healthproyect.dto.country;

import com.spacecodee.healthproyect.model.countries.CountryModel;
import javafx.util.StringConverter;

public class CountryConverter extends StringConverter<CountryModel> {

    @Override
    public String toString(CountryModel countryModel) {
        return countryModel != null ? countryModel.getCountry() : null;
    }

    @Override
    public CountryModel fromString(String s) {
        return null;
    }
}
