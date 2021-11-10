package com.spacecodee.healthproyect.model.cities;

import lombok.*;

import java.io.Serializable;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CityModel implements Serializable {

    @Getter
    private int idCity;
    @Getter
    @Setter
    private String name;

    public CityModel(int idCity) {
        this.idCity = idCity;
    }
}
