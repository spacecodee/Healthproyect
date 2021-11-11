package com.spacecodee.healthproyect.dao.districs;

import com.spacecodee.healthproyect.dao.ICrudGeneric;
import com.spacecodee.healthproyect.dto.district.DistrictTable;
import com.spacecodee.healthproyect.model.districts.DistrictModel;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public interface IDistrictDao extends ICrudGeneric<DistrictModel> {

    ObservableList<DistrictTable> loadTable();

    ObservableList<DistrictTable> findByNameTable(String districtName);

    ArrayList<DistrictModel> listOfDistrict();

    ArrayList<DistrictModel> listOfDistrict(int id);

    int getMaxId();
}
