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
    private String country;

    public CountryModel(Integer idCountry) {
        this.idCountry = idCountry;
    }

    public CountryModel(String country) {
        this.country = country;
    }

}
