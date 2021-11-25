package com.spacecodee.healthproyect.model.reserved_days;

import lombok.*;

import java.io.Serializable;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReservedDaysModel implements Serializable {

    @Getter
    private int idReservedDay;
    @Getter
    @Setter
    private String reservationDate;
}
