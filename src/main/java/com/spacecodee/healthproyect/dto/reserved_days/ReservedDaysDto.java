package com.spacecodee.healthproyect.dto.reserved_days;

import lombok.*;

import java.io.Serializable;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReservedDaysDto implements Serializable {

    @Getter
    @Setter
    private int idReservedDay;
    @Getter
    @Setter
    private String reservationDate;

    public ReservedDaysDto(int idReservedDay) {
        this.idReservedDay = idReservedDay;
    }
}
