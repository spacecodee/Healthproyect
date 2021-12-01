package com.spacecodee.healthproyect.dto.address;

import com.spacecodee.healthproyect.dto.districts.DistrictDto;
import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    @Getter
    @Setter
    private int idAddress;
    @Getter
    @Setter
    private String addressName;
    @Getter
    @Setter
    private DistrictDto districtDto;

    public AddressDto(int idAddress) {
        this.idAddress = idAddress;
    }

    public AddressDto(String addressName, DistrictDto districtDto) {
        this.addressName = addressName;
        this.districtDto = districtDto;
    }

    public AddressDto(DistrictDto districtDto) {
        this.districtDto = districtDto;
    }
}
