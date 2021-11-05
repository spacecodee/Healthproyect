package com.spacecodee.healthproyect.model.districts;

import lombok.*;

import java.io.Serializable;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DistrictModel implements Serializable {

    @Getter
    private int idDistrict;
    @Getter
    @Setter
    private String name;
}
