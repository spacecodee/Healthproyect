package com.spacecodee.healthproyect.converters.type_reservations;

import com.spacecodee.healthproyect.dto.type_reservations.TypeReservationDto;
import javafx.util.StringConverter;

public class TypeReservationConverter extends StringConverter<TypeReservationDto> {
    private static final String MONEY = "S/.";

    @Override
    public String toString(TypeReservationDto typeReservationDto) {
        return typeReservationDto != null
                ? typeReservationDto.getNameReservation() + " | "
                + TypeReservationConverter.MONEY + typeReservationDto.getPriceReservation()
                : null;
    }

    @Override
    public TypeReservationDto fromString(String s) {
        return null;
    }
}
