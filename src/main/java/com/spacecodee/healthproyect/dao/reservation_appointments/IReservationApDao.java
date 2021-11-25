package com.spacecodee.healthproyect.dao.reservation_appointments;

import com.spacecodee.healthproyect.dao.ICrudGeneric;
import com.spacecodee.healthproyect.dto.customer.CustomerTable;
import com.spacecodee.healthproyect.dto.reservation_appointments.ReservationTable;
import com.spacecodee.healthproyect.model.reservation_appointments.ReservationApModel;
import javafx.collections.ObservableList;

public interface IReservationApDao extends ICrudGeneric<ReservationApModel> {

    ObservableList<ReservationTable> loadTable();

    ObservableList<ReservationTable> findReservation(ReservationTable customerTable);
}
