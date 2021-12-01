package com.spacecodee.healthproyect.dto.districts;

import com.spacecodee.healthproyect.dto.cities.CityDto;
import lombok.*;

import java.io.Serializable;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DistrictDto implements Serializable {

    @Getter
    private int idDistrict;
    @Getter
    @Setter
    private String districtName;
    @Getter
    @Setter
    private CityDto cityDto;

    public DistrictDto(int idDistrict) {
        this.idDistrict = idDistrict;
    }

    public DistrictDto(String districtName) {
        this.districtName = districtName;
    }

    public DistrictDto(int idDistrict, String districtName) {
        this.idDistrict = idDistrict;
        this.districtName = districtName;
    }

    public DistrictDto(String districtName, CityDto cityDto) {
        this.districtName = districtName;
        this.cityDto = cityDto;
    }
}
