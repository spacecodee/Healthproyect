package com.spacecodee.healthproyect.model.reservation_appointments;

import com.spacecodee.healthproyect.model.customers.CustomerModel;
import lombok.*;

import java.io.Serializable;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReservationAppointments implements Serializable {

    @Getter
    private int reservationQuote;

    @Getter
    @Setter
    private CustomerModel customer;
}
