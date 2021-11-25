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
}
