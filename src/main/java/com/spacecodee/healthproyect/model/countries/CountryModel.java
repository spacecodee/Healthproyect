package com.spacecodee.healthproyect.model.countries;

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
    private String countryName;

    public CountryModel(Integer idCountry) {
        this.idCountry = idCountry;
    }

    public CountryModel(String countryName) {
        this.countryName = countryName;
    }

}
