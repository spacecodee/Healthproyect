package com.spacecodee.healthproyect.model.cities;

import com.spacecodee.healthproyect.model.districts.DistrictModel;
import com.spacecodee.healthproyect.model.postal_codes.PostalCodeModel;
import lombok.*;

import java.io.Serializable;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CityModel implements Serializable {

    @Getter
    private int idCity;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private PostalCodeModel postalCodeModel;
    @Getter
    @Setter
    private DistrictModel districtModel;

    public CityModel(String name) {
        this.name = name;
    }
}
