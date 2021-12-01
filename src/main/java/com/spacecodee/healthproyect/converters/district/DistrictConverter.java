package com.spacecodee.healthproyect.converters.district;

import com.spacecodee.healthproyect.dto.districts.DistrictDto;
import javafx.util.StringConverter;

public class DistrictConverter extends StringConverter<DistrictDto> {
    @Override
    public String toString(DistrictDto districtDto) {
        return districtDto != null ? districtDto.getDistrictName() : null;
    }

    @Override
    public DistrictDto fromString(String s) {
        return null;
    }
}
