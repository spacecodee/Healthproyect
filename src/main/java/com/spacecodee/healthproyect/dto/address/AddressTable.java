package com.spacecodee.healthproyect.dto.address;

import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AddressTable {

    @Getter
    private int idAddress;
    @Getter
    @Setter
    private String countryName;
    @Getter
    @Setter
    private String cityName;
    @Getter
    @Setter
    private String districtName;
    @Getter
    @Setter
    private String addressName;
}
