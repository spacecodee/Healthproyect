package com.spacecodee.healthproyect.dao.type_reservations;

import com.spacecodee.healthproyect.dao.ICrudGeneric;
import com.spacecodee.healthproyect.dto.type_reservations.TypeReservationDto;

public interface ITypeReservationsDao extends ICrudGeneric<TypeReservationDto> {

    int total();

    int returnMaxId();
}
