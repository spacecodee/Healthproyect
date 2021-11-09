package com.spacecodee.healthproyect.model.address;

import com.spacecodee.healthproyect.model.cities.CityModel;
import com.spacecodee.healthproyect.model.countries.CountryModel;
import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AddressModel {

    @Getter
    private int idAddress;
    @Getter
    @Setter
    private CityModel cityModel;
    @Getter
    @Setter
    private CountryModel countryModel;
}
