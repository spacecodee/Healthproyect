package com.spacecodee.healthproyect.converters.city;

import com.spacecodee.healthproyect.dto.cities.CityDto;
import javafx.util.StringConverter;

public class CityConverter extends StringConverter<CityDto> {

    @Override
    public String toString(CityDto cityDto) {
        return cityDto != null ? cityDto.getCityName() : null;
    }

    @Override
    public CityDto fromString(String s) {
        return null;
    }
}
