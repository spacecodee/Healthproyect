package com.spacecodee.healthproyect.model.type_reservations;

import lombok.*;

import java.io.Serializable;

@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TypeReservationModel implements Serializable {

    @Getter
    private int idTypeReservation;
    @Getter
    @Setter
    private String nameReservation;
    @Getter
    @Setter
    private double priceReservation;

    public TypeReservationModel(int idTypeReservation) {
        this.idTypeReservation = idTypeReservation;
    }

    public TypeReservationModel(String nameReservation) {
        this.nameReservation = nameReservation;
    }

    public TypeReservationModel(String nameReservation, double priceReservation) {
        this.nameReservation = nameReservation;
        this.priceReservation = priceReservation;
    }
}
