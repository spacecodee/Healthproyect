package com.spacecodee.healthproyect.model.cities;

import com.spacecodee.healthproyect.model.countries.CountryModel;
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
    private CountryModel countryModel;

    public CityModel(int idCity) {
        this.idCity = idCity;
    }

    public CityModel(String name) {
        this.name = name;
    }
}
