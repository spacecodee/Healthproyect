package com.spacecodee.healthproyect.dto.type_reservations;

import lombok.*;

import java.io.Serializable;

@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TypeReservationDto implements Serializable {

    @Getter
    @Setter
    private int idTypeReservation;
    @Getter
    @Setter
    private String nameReservation;
    @Getter
    @Setter
    private double priceReservation;

    public TypeReservationDto(int idTypeReservation) {
        this.idTypeReservation = idTypeReservation;
    }

    public TypeReservationDto(String nameReservation) {
        this.nameReservation = nameReservation;
    }

    public TypeReservationDto(String nameReservation, double priceReservation) {
        this.nameReservation = nameReservation;
        this.priceReservation = priceReservation;
    }
}
