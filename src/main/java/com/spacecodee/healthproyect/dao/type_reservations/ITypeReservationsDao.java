package com.spacecodee.healthproyect.dao.type_reservations;

import com.spacecodee.healthproyect.dao.ICrudGeneric;
import com.spacecodee.healthproyect.model.type_reservations.TypeReservationModel;

import java.util.ArrayList;

public interface ITypeReservationsDao extends ICrudGeneric<TypeReservationModel> {

    ArrayList<TypeReservationModel> listOfTypeReservations();
}
