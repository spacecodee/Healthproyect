package com.spacecodee.healthproyect.converters.country;

import com.spacecodee.healthproyect.dto.countries.CountryDto;
import javafx.util.StringConverter;

public class CountryConverter extends StringConverter<CountryDto> {

    @Override
    public String toString(CountryDto countryDto) {
        return countryDto != null ? countryDto.getCountryName() : null;
    }

    @Override
    public CountryDto fromString(String s) {
        return null;
    }
}
