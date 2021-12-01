package com.spacecodee.healthproyect.dto.cities;

import com.spacecodee.healthproyect.dto.countries.CountryDto;
import lombok.*;

import java.io.Serializable;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CityDto implements Serializable {

    @Getter
    private int idCity;
    @Getter
    @Setter
    private String cityName;
    @Getter
    @Setter
    private CountryDto countryDto;

    public CityDto(int idCity) {
        this.idCity = idCity;
    }

    public CityDto(String cityName) {
        this.cityName = cityName;
    }

    public CityDto(String cityName, CountryDto countryDto) {
        this.cityName = cityName;
        this.countryDto = countryDto;
    }
}
