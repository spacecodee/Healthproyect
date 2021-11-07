package com.spacecodee.healthproyect.model.countries;

import com.spacecodee.healthproyect.model.cities.CityModel;
import lombok.*;

import java.io.Serializable;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CountryModel implements Serializable {

    @Getter
    private Integer idCountry;
    @Getter
    @Setter
    private String country;
    @Getter
    @Setter
    private CityModel cityModel;
}
