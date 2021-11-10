package com.spacecodee.healthproyect.model.districts;

import com.spacecodee.healthproyect.model.cities.CityModel;
import lombok.*;

import java.io.Serializable;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DistrictModel implements Serializable {

    @Getter
    private int idDistrict;
    @Getter
    @Setter
    private String districtName;
    @Getter
    @Setter
    private CityModel cityModel;

    public DistrictModel(String districtName) {
        this.districtName = districtName;
    }

    public DistrictModel(int idDistrict, String districtName) {
        this.idDistrict = idDistrict;
        this.districtName = districtName;
    }
}
