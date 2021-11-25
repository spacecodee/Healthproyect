package com.spacecodee.healthproyect.model.reservation_appointments;

import com.spacecodee.healthproyect.model.customers.CustomerModel;
import com.spacecodee.healthproyect.model.reserved_days.ReservedDaysModel;
import com.spacecodee.healthproyect.model.type_reservations.TypeReservationModel;
import com.spacecodee.healthproyect.model.users.UserModel;
import lombok.*;

import java.io.Serializable;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReservationApModel implements Serializable {

    @Getter
    private int idReservationQuote;
    @Getter
    @Setter
    private CustomerModel customer;
    @Getter
    @Setter
    private UserModel user;
    @Getter
    @Setter
    private TypeReservationModel typeReservation;
    @Getter
    @Setter
    private ReservedDaysModel reservedDays;
}
