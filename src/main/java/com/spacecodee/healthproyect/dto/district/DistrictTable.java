package com.spacecodee.healthproyect.dto.district;

import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DistrictTable {

    @Getter
    private int idDistrict;
    @Getter
    @Setter
    private String districtName;
    @Getter
    @Setter
    private String cityName;
    @Getter
    @Setter
    private String countryName;

}
