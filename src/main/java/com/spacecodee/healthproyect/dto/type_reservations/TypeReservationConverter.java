package com.spacecodee.healthproyect.dto.type_reservations;

import com.spacecodee.healthproyect.model.type_reservations.TypeReservationModel;
import javafx.util.StringConverter;

public class TypeReservationConverter extends StringConverter<TypeReservationModel> {
    @Override
    public String toString(TypeReservationModel typeReservationModel) {
        return typeReservationModel != null ? typeReservationModel.getNameReservation() : null;
    }

    @Override
    public TypeReservationModel fromString(String s) {
        return null;
    }
}
