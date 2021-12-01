package com.spacecodee.healthproyect.dto.reservation_appointments;

import com.spacecodee.healthproyect.dto.customers.CustomerDto;
import com.spacecodee.healthproyect.dto.reserved_days.ReservedDaysDto;
import com.spacecodee.healthproyect.dto.type_reservations.TypeReservationDto;
import com.spacecodee.healthproyect.dto.users.UserDto;
import lombok.*;

import java.io.Serializable;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReservationApDto implements Serializable {

    @Getter
    @Setter
    private int idReservationQuote;
    @Getter
    @Setter
    private CustomerDto customer;
    @Getter
    @Setter
    private UserDto user;
    @Getter
    @Setter
    private TypeReservationDto typeReservation;
    @Getter
    @Setter
    private ReservedDaysDto reservedDays;

    public ReservationApDto(int idReservationQuote) {
        this.idReservationQuote = idReservationQuote;
    }

    public ReservationApDto(CustomerDto customer) {
        this.customer = customer;
    }
}
