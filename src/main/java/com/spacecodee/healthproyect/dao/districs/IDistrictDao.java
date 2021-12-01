package com.spacecodee.healthproyect.dao.districs;

import com.spacecodee.healthproyect.dao.ICrudGeneric;
import com.spacecodee.healthproyect.dto.districts.DistrictDto;

import java.util.ArrayList;

public interface IDistrictDao extends ICrudGeneric<DistrictDto> {

    ArrayList<DistrictDto> listOfDistrict(int id);

    int getMaxId();
}
