package com.spacecodee.healthproyect.dao.districs;

import com.spacecodee.healthproyect.dao.ICrudGeneric;
import com.spacecodee.healthproyect.model.districts.DistrictModel;

import java.util.ArrayList;

public interface IDistrictDao extends ICrudGeneric<DistrictModel> {

    ArrayList<DistrictModel> listOfDistrict(int id);

    int getMaxId();
}
