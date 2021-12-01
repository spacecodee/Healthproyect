package com.spacecodee.healthproyect.converters.reservation_appointments;

import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReservationTable {

    @Getter
    @Setter
    private int idReservationAppointment;
    @Getter
    @Setter
    private int idPeople;
    @Getter
    @Setter
    private int idCustomer;
    @Getter
    @Setter
    private int idUser;
    @Getter
    @Setter
    private int idTypeReservation;
    @Getter
    @Setter
    private int idReservationDate;
    @Getter
    @Setter
    private String customerName;
    @Getter
    @Setter
    private String customerLastName;
    @Getter
    @Setter
    private String customerDni;
    @Getter
    @Setter
    private String typeReservation;
    @Getter
    @Setter
    private double priceReservation;
    @Getter
    @Setter
    private String dateReservation;
    @Getter
    @Setter
    private String userName;
    @Getter
    @Setter
    private String userDni;
}
