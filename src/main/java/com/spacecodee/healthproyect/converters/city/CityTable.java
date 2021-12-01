package com.spacecodee.healthproyect.converters.city;

import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CityTable {

    @Getter
    private int idCity;
    @Getter
    @Setter
    private String cityName;
    @Getter
    @Setter
    private String countryName;
}
