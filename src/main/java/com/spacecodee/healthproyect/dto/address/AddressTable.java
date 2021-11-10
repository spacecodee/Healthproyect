package com.spacecodee.healthproyect.dto.address;

import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AddressTable {

    @Getter
    private int idAddress;
    @Getter
    private Integer idCountry;
    @Getter
    @Setter
    private String countryName;
    @Getter
    private int idCity;
    @Getter
    @Setter
    private String cityName;
    @Getter
    private int idDistrict;
    @Getter
    @Setter
    private String districtName;
}
