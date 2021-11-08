package com.spacecodee.healthproyect.model.countries;

import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CountryTable {

    @Getter
    private Integer idCountry;
    @Getter
    @Setter
    private String country;
    @Getter
    private int idCity;
    @Getter
    @Setter
    private String cityName;
    @Getter
    private int idPostalCode;
    @Getter
    @Setter
    private String postalCode;
    @Getter
    private int idDistrict;
    @Getter
    @Setter
    private String districtName;
}
