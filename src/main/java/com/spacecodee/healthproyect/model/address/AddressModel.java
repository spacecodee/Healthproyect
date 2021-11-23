package com.spacecodee.healthproyect.model.address;

import com.spacecodee.healthproyect.model.districts.DistrictModel;
import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AddressModel {

    @Getter
    @Setter
    private int idAddress;
    @Getter
    @Setter
    private String addressName;
    @Getter
    @Setter
    private DistrictModel districtModel;

    public AddressModel(int idAddress) {
        this.idAddress = idAddress;
    }

    public AddressModel(String addressName, DistrictModel districtModel) {
        this.addressName = addressName;
        this.districtModel = districtModel;
    }
}
