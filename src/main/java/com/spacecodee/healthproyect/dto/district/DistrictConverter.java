package com.spacecodee.healthproyect.dto.district;

import com.spacecodee.healthproyect.model.districts.DistrictModel;
import javafx.util.StringConverter;

public class DistrictConverter extends StringConverter<DistrictModel> {
    @Override
    public String toString(DistrictModel districtModel) {
        return districtModel != null ? districtModel.getDistrictName() : null;
    }

    @Override
    public DistrictModel fromString(String s) {
        return null;
    }
}
