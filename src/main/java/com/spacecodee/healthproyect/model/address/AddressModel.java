package com.spacecodee.healthproyect.model.address;

import com.spacecodee.healthproyect.model.cities.CityModel;
import com.spacecodee.healthproyect.model.countries.CountryModel;
import com.spacecodee.healthproyect.model.districts.DistrictModel;
import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AddressModel {

    @Getter
    private int idAddress;
    @Getter
    @Setter
    private CountryModel countryModel;
    @Getter
    @Setter
    private CityModel cityModel;
    @Getter
    @Setter
    private DistrictModel districtModel;

    public AddressModel(int idAddress) {
        this.idAddress = idAddress;
    }

    public AddressModel(CountryModel countryModel, CityModel cityModel, DistrictModel districtModel) {
        this.countryModel = countryModel;
        this.cityModel = cityModel;
        this.districtModel = districtModel;
    }
}
