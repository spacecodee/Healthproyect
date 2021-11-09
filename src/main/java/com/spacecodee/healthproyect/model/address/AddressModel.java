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

    public AddressModel(int idAddress) {
        this.idAddress = idAddress;
    }

    public AddressModel(CityModel cityModel, CountryModel countryModel) {
        this.cityModel = cityModel;
        this.countryModel = countryModel;
    }
}
