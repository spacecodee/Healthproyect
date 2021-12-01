package com.spacecodee.healthproyect.dto.countries;

import lombok.*;

import java.io.Serializable;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CountryDto implements Serializable {

    @Getter
    private Integer idCountry;
    @Getter
    @Setter
    private String countryName;

    public CountryDto(Integer idCountry) {
        this.idCountry = idCountry;
    }

    public CountryDto(String countryName) {
        this.countryName = countryName;
    }

}
